package com.redhat.servlets;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.yaml.snakeyaml.error.YAMLException;

import com.redhat.rag.yaml.YamlUtil;
import com.redhat.rag.yaml.model.KnowledgeQnA;
import com.redhat.rag.yaml.model.SkillQnA;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet(name = "FileUploadServlet", urlPatterns = { "/fileupload" }) 
@MultipartConfig 
public class FileUploadServlet extends GenericServlet 
  {
    static String _TARGET_404 = null;
    static String _TARGET_KQNA = null;
    static String _TARGET_SQNA = null;
    static String _QNA_STUBFILE = null;
    static boolean _QNA_STUB_ENABLED = false;
    static String _CALLING_PAGE = null;

    @Override public void init() throws ServletException 
      {
        _TARGET_404 = getConfig("http.status_code.404");
        _TARGET_KQNA = getConfig("fwd.target.jsp.qna");
        _TARGET_SQNA = getConfig("fwd.target.jsp.skill.qna");
        _CALLING_PAGE = getConfig("servlet.FileUploadServlet.callingPage");
        _QNA_STUBFILE = getConfig("servlet.FileUploadServlet.qna.stubfile");
        _QNA_STUB_ENABLED = "true".equalsIgnoreCase(getConfig("servlet.FileUploadServlet.enable.qna.stubfile"));

        super.init();
      }

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
      {
        KnowledgeQnA qnaDoc = null;
        HttpSession session = null;
        RequestDispatcher dispatcher = null;

        if(!_QNA_STUB_ENABLED) 
          {
            dispatcher = getServletContext().getRequestDispatcher(_CALLING_PAGE);
            dispatcher.forward(request, response);
            return;
          }

        try 
          {
            qnaDoc = YamlUtil.parseKnowledgeYamlFromFile(_QNA_STUBFILE);
          } 
        catch (FileNotFoundException ex) 
          {
            System.err.println(ex);

            /*
             * If configured file is no good, send to file selection page
             */
            dispatcher = getServletContext().getRequestDispatcher(_CALLING_PAGE);
            dispatcher.forward(request, response);    
          }

        session = request.getSession();
        session.setAttribute("qnaDoc", qnaDoc);
        dispatcher = getServletContext().getRequestDispatcher(_TARGET_KQNA);
        dispatcher.forward(request, response);
      }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
      {
        RequestDispatcher dispatcher = null;
        KnowledgeQnA qnaDoc = null;
        SkillQnA qnaSDoc = null;
        HttpSession session = null;
        Part filePart = null;
        InputStream fileStream = null;
        String target = _CALLING_PAGE;
        String file = null;
        String version = "3";

        filePart = request.getPart("myfile");

        if(!"".equals(filePart.getSubmittedFileName()))
          {
            session = request.getSession();
            fileStream = filePart.getInputStream();
            file = loadFile(fileStream);
            version = getVersion(file);

            if("3".equals(version))
              {
                try
                  {
                    qnaDoc = YamlUtil.parseKnowledgeYamlFromString(file);
                    YamlUtil.padQnA(qnaDoc);
                    session.setAttribute("qnaDoc", qnaDoc);
                    target = _TARGET_KQNA;
                  }
                catch(YAMLException ex)
                  {
                    // must be a Skill qna
                    version = "2";
                  }
              }
            
            if("2".equals(version))
              { 
                try
                  {
                    qnaSDoc = YamlUtil.parseSkillYamlFromString(file);
                    YamlUtil.padQnA(qnaSDoc);
                    session.setAttribute("qnaSDoc", qnaSDoc);
                    target = _TARGET_SQNA;
                  }
                catch(YAMLException ex)
                  {
                    System.err.println(ex);
                  }
              }
            dispatcher = getServletContext().getRequestDispatcher(target);
            dispatcher.forward(request, response);
          }

        dispatcher = getServletContext().getRequestDispatcher(target);
        dispatcher.forward(request, response);
      }

    private String loadFile(InputStream inputStream) throws IOException 
      {
        StringBuilder resultStringBuilder = null;
        String line = null;

        resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) 
          {
            while((line = br.readLine()) != null) 
              resultStringBuilder.append(line).append("\n");
          }
          
        return(resultStringBuilder.toString());
      }
    
    private static String getVersion(String file)
      {
        String match = "version:";
        String value = null;
        int startIndex, endIndex;

        startIndex = (file.trim()).indexOf(match);
        if(startIndex != -1)
          {
            endIndex = (file.trim()).indexOf("\n", startIndex);
            if(endIndex != -1)
              value = file.substring(startIndex + match.length(), endIndex).trim();
            else
              value = file.substring(startIndex + match.length()).trim(); // end of file
          }

        return(value);
      }


}
