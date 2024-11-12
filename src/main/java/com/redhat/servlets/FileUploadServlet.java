package com.redhat.servlets;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.redhat.rad.yaml.YamlUtil;
import com.redhat.rad.yaml.model.QnA;

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
    static String _TARGET = null;
    static String _QNA_STUBFILE = null;
    static boolean _QNA_STUB_ENABLED = false;
    static String _CALLING_PAGE = null;

    @Override public void init() throws ServletException 
      {
        _TARGET_404 = getConfig("http.status_code.404");
        _TARGET = getConfig("servlet.FileUploadServlet.target");
        _CALLING_PAGE = getConfig("servlet.FileUploadServlet.callingPage");
        _QNA_STUBFILE = getConfig("servlet.FileUploadServlet.qna.stubfile");
        _QNA_STUB_ENABLED = "true".equalsIgnoreCase(getConfig("servlet.FileUploadServlet.enable.qna.stubfile"));

        super.init();
      }

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
      {
        QnA qnaDoc = null;
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
            qnaDoc = YamlUtil.parseYaml(_QNA_STUBFILE);
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
        dispatcher = getServletContext().getRequestDispatcher(_TARGET);
        dispatcher.forward(request, response);
      }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
      {
        RequestDispatcher dispatcher = null;
        QnA qnaDoc = null;
        HttpSession session = null;
        Part filePart = null;
        InputStream fileStream = null;

        filePart = request.getPart("myfile");

        if(!"".equals(filePart.getSubmittedFileName()))
          {
            fileStream = filePart.getInputStream();
            qnaDoc = YamlUtil.parseYaml(fileStream);

            session = request.getSession();
            session.setAttribute("qnaDoc", qnaDoc);
            dispatcher = getServletContext().getRequestDispatcher(_TARGET);
            dispatcher.forward(request, response);
          }

        dispatcher = getServletContext().getRequestDispatcher(_CALLING_PAGE);
        dispatcher.forward(request, response);
      }
}
