package com.redhat.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.redhat.rag.yaml.YamlUtil;
import com.redhat.rag.yaml.model.Document;
import com.redhat.rag.yaml.model.KnowledgeQnA;
import com.redhat.rag.yaml.model.KnowledgeSeedExample;
import com.redhat.rag.yaml.model.KnowledgeSeedQandA;
import com.redhat.rag.yaml.model.SkillQnA;
import com.redhat.rag.yaml.model.SkillSeedExample;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "FileSaveServlet", urlPatterns = { "/filesave" }) 
@MultipartConfig 
public class FileSaveServlet extends GenericServlet 
  {
    static String _TARGET_404 = null;
    static String _TARGET_KQNA = null;
    static String _TARGET_SQNA = null;
    static String _QNA_STUBFILE = null;
    static boolean _QNA_STUB_ENABLED = false;
    static String _CALLING_PAGE = null;

    @Override 
    public void init() throws ServletException 
      {
        _TARGET_404 = getConfig("http.status_code.404");
        _TARGET_KQNA = getConfig("fwd.target.jsp.qna");
        _TARGET_SQNA = getConfig("fwd.target.jsp.skill.qna");
        _CALLING_PAGE = getConfig("servlet.FileUploadServlet.callingPage");
        _QNA_STUBFILE = getConfig("servlet.FileUploadServlet.qna.stubfile");
        _QNA_STUB_ENABLED = "true".equalsIgnoreCase(getConfig("servlet.FileUploadServlet.enable.qna.stubfile"));

        super.init();
      }

    @Override 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
      {
        doProcess(request, response);
      }

    @Override 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
      {
        doProcess(request, response);
      }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
      {
        RequestDispatcher dispatcher = null;
        KnowledgeQnA qnaDoc = null;
        SkillQnA sQnaDoc = null;
        HttpSession session = null;
        String btnExport = null;
        String btnPreview = null;
        String yaml = null;
        String fwdTarget = _TARGET_404;
      
        session = request.getSession();
        btnPreview = request.getParameter("preview");
        btnExport = request.getParameter("export");

        if((qnaDoc = (KnowledgeQnA)session.getAttribute("qnaDoc")) != null)
          {
            /* 
             * Perform save function
             */
            qnaDoc = buildKnowledgeQnA(request, response);
            session.setAttribute("qnaDoc", qnaDoc);

            if((btnPreview != null) && (qnaDoc != null))
              {
                qnaDoc = YamlUtil.cloneQnA(qnaDoc);
                YamlUtil.pruneEmptyQnA(qnaDoc);
                yaml = YamlUtil.encodeYaml(qnaDoc);
                yaml = YamlUtil.encodeYamlAsHTML(yaml);
                request.setAttribute("yaml", yaml);
              }

            if(btnExport == null)
              fwdTarget = _TARGET_KQNA;
            else
              fwdTarget = "/filedownload";
            
          }
        else if((sQnaDoc = (SkillQnA)session.getAttribute("qnaSDoc")) != null)
          {
            /* 
             * Perform save function
             */
            sQnaDoc = buildSkillQnA(request, response);
            session.setAttribute("qnaSDoc", sQnaDoc);

            if((btnPreview != null) && (sQnaDoc != null))
              {
                sQnaDoc = YamlUtil.cloneQnA(sQnaDoc);
                YamlUtil.pruneEmptyQnA(sQnaDoc);
                yaml = YamlUtil.encodeYaml(sQnaDoc);
                yaml = YamlUtil.pruneEmptyContext(yaml);
                yaml = YamlUtil.encodeYamlAsHTML(yaml);
                request.setAttribute("yaml", yaml);
              }

             if(btnExport == null)
               fwdTarget = _TARGET_SQNA;
             else
               fwdTarget = "/filedownload";
            
          }

        // to-do: change forward to session timeout page
        dispatcher = getServletContext().getRequestDispatcher(fwdTarget);
        dispatcher.forward(request, response);
      }

    private KnowledgeQnA buildKnowledgeQnA(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
        {
          KnowledgeQnA qnaDoc = null;
          KnowledgeSeedExample seedEx = null;
          KnowledgeSeedQandA seedQandA = null;
          List<KnowledgeSeedExample> seedExList = new ArrayList<KnowledgeSeedExample>();
          List<KnowledgeSeedQandA> seedQandAList = null;
          Document doc = null;
          List<String> docPatterns = null;
          String value = null;
          String[] values = null;
          int version;

          qnaDoc = new KnowledgeQnA();

          doc = new Document();
          docPatterns = new ArrayList<String>();
          value = getParameter(request, "patterns");
          if(value != null)
            {
              values = value.split(",", 0);
              for(int i=0; i<values.length; i++)
                docPatterns.add(values[i].trim());
            }
          else
            docPatterns.add("");
          doc.setPatterns(docPatterns);
          doc.setRepo(getParameter(request, "repo"));
          doc.setCommit(getParameter(request, "commit"));
          qnaDoc.setDocument(doc);

          for(int i=0; i<5; i++)
            {
              // test for cases where less than 5 seed_examples have been set
              value = getParameter(request, "context_" + i);
              if(value == null)
                break;

              seedEx = new KnowledgeSeedExample();
              seedEx.setContext(value);

              seedQandAList = new ArrayList<KnowledgeSeedQandA>();
              for(int j=0; j<3; j++)
                {
                  // test for cases where less than 3 question and answer pairs have been set
                  value = getParameter(request, "question_" + i + "_" + j);
                  if(value == null)
                    break;

                  seedQandA = new KnowledgeSeedQandA();
                  seedQandA.setQuestion(value);
                  seedQandA.setAnswer(getParameter(request, "answer_" + i + "_" + j));
                  seedQandAList.add(seedQandA);
                }

              seedEx.setQuestions_and_answers(seedQandAList);
              seedExList.add(seedEx);
            }
          
          qnaDoc.setSeed_examples(seedExList);
          version = Integer.parseInt(getParameter(request, "version"));
          qnaDoc.setVersion(version);
          qnaDoc.setDomain(getParameter(request, "domain"));
          qnaDoc.setCreated_by(getParameter(request, "created_by"));
          qnaDoc.setDocument_outline(getParameter(request, "outline"));
      
          return (qnaDoc);
        }

    private SkillQnA buildSkillQnA(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
      {
        SkillQnA qnaDoc = null;
        SkillSeedExample seedEx = null;
        List<SkillSeedExample> seedExList = new ArrayList<SkillSeedExample>();
        String value = null;
        int version;

        qnaDoc = new SkillQnA();

        for(int i=0; i<5; i++)
          {
            // test for cases where less than 5 seed_examples have been set
            value = getParameter(request, "question_" + i);
            if(value == null)
              break;

            seedEx = new SkillSeedExample();
            seedEx.setQuestion(value);
            seedEx.setAnswer(getParameter(request, "answer_" + i));
            seedEx.setContext(getParameter(request, "context_" + i));
            seedExList.add(seedEx);
          }
        
        qnaDoc.setSeed_examples(seedExList);
        version = Integer.parseInt(getParameter(request, "version"));
        qnaDoc.setVersion(version);
        qnaDoc.setCreated_by(getParameter(request, "created_by"));
        qnaDoc.setTask_description(getParameter(request, "task_description"));
    
        return (qnaDoc);
      }

    private String getParameter(HttpServletRequest request, String name)
      {
        String valString = null;

        valString = request.getParameter(name);
        if(valString != null)
          {
            valString = valString.trim();
            valString = valString.replaceAll("\r\n", "\n");
            valString = YamlUtil.escapeQuotes(valString);
          }

        return(valString);
      }
  }
