package com.redhat.servlets;

import java.io.IOException;

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

@WebServlet(name = "NewFileServlet", urlPatterns = { "/newfile" }) 
@MultipartConfig 
public class NewFileServlet extends GenericServlet 
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

    @Override 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
      {
        doProcess(request, response);
      }
 
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
      {
        RequestDispatcher dispatcher = null;
        KnowledgeQnA qnaKDoc = null;
        SkillQnA qnaSDoc = null;
        HttpSession session = null;
        int version;
        String target = null;

        session = request.getSession();
        version = Integer.parseInt(request.getParameter("version"));

        if(version == 3)
          {
            qnaKDoc = YamlUtil.createEmptyKQnA();
            qnaKDoc.setVersion(version);
            session.setAttribute("qnaDoc", qnaKDoc);
            session.setAttribute("qnaSDoc", null);
            target = _TARGET_KQNA;
          }
        else
          {
            qnaSDoc = YamlUtil.createEmptySQnA(); 
            qnaSDoc.setVersion(version);
            session.setAttribute("qnaSDoc", qnaSDoc);
            session.setAttribute("qnaDoc", null);
            target = _TARGET_SQNA;
          }

        dispatcher = getServletContext().getRequestDispatcher(target);
        dispatcher.forward(request, response);
      }
  }
