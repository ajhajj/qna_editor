package com.redhat.servlets;

import java.io.IOException;
import java.io.OutputStream;

import com.redhat.rad.yaml.YamlUtil;
import com.redhat.rad.yaml.model.KnowledgeQnA;
import com.redhat.rad.yaml.model.SkillQnA;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "FileDownloadServlet", urlPatterns = { "/filedownload" }) 
@MultipartConfig 
public class FileDownloadServlet extends GenericServlet 
  {
    static String _TARGET_404 = null;
    static String _TARGET = null;
    static String _QNA_STUBFILE = null;
    static boolean _QNA_STUB_ENABLED = false;
    static String _CALLING_PAGE = null;

    @Override public void init() throws ServletException 
      {
        _TARGET_404 = getConfig("http.status_code.404");
        _TARGET = getConfig("fwd.target.jsp.qna");
        _CALLING_PAGE = getConfig("servlet.FileUploadServlet.callingPage");
        _QNA_STUBFILE = getConfig("servlet.FileUploadServlet.qna.stubfile");
        _QNA_STUB_ENABLED = "true".equalsIgnoreCase(getConfig("servlet.FileUploadServlet.enable.qna.stubfile"));

        super.init();
      }

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
      {
        doProcess(request, response);      }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
      {
        doProcess(request, response);
      }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
      {
        RequestDispatcher dispatcher = null;
        KnowledgeQnA qnaDoc = null;
        SkillQnA qnaSDoc = null;
        HttpSession session = null;
        String yaml = null;
        byte[] yaml_bytes = null;
        boolean qnaNull = true;
      
        session = request.getSession();
        if((qnaDoc = (KnowledgeQnA)session.getAttribute("qnaDoc")) != null)
          {
            YamlUtil.pruneEmptyQnA(qnaDoc);
            yaml = YamlUtil.encodeYaml(qnaDoc);
            qnaNull = false;
          }
        else if((qnaSDoc = (SkillQnA)session.getAttribute("qnaSDoc")) != null)
          {
            YamlUtil.pruneEmptyQnA(qnaSDoc);
            yaml = YamlUtil.encodeYaml(qnaSDoc);
            yaml = pruneEmptyContext(yaml);
            qnaNull = false;
          }

        if(!qnaNull)
          {
            response.setContentType("text/plain");
            response.setHeader("Content-disposition", "attachment; filename=qna.yaml");
    
            yaml_bytes = yaml.getBytes();

            try(OutputStream out = response.getOutputStream();)
              {
                out.write(yaml_bytes);
              }
            
            return;
          }

        dispatcher = getServletContext().getRequestDispatcher(_TARGET_404);
        dispatcher.forward(request, response);
      }
    
    public static String pruneEmptyContext(String yamlString)
      {
        String token = "    context: ''";
        int startIndex, endIndex;

        while((startIndex = yamlString.indexOf(token)) != -1)
          {
            endIndex = yamlString.indexOf("\n", startIndex);
            if(endIndex == -1)
              yamlString = yamlString.substring(startIndex);
            else
              yamlString = yamlString.substring(0, startIndex) + yamlString.substring( endIndex + 1);
          }

        return(yamlString);
      }
}
