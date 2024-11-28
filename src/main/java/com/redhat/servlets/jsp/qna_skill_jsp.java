package com.redhat.servlets.jsp;

import com.redhat.rad.yaml.model.SkillQnA;
import com.redhat.rad.yaml.model.SkillSeedExample;
import java.util.List;
import java.util.Iterator;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet(
    name = "qna_skill_jsp",
    urlPatterns = {"/qna_skill_jsp"}
  )
public class qna_skill_jsp extends HttpServlet //initial
  {
    private static final long serialVersionUID = 1L;


//<DECLARATIONS>
//<SERVLETINFO>
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
      doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
      {
        ServletContext application = null;
        ServletConfig config = null;
        PrintWriter out = null;
        Object page = null;
        //PageContext pageContext = null;
        HttpSession session = request.getSession();
        String contentType = "text/html"; //initial
        String errorPage = null; //initial
        //isErrorPage
        
        application = request.getServletContext();
        config = getServletConfig();
        out = response.getWriter();
        page = this;
        response.setContentType(contentType);
        // setCharacterEncoding


        try
          {
            out.print("<!DOCTYPE html>\n");
            out.print("<html lang=\"en\">\n");
            out.print(" ");
            out.print(" ");
            SkillQnA qnaDoc = (SkillQnA)session.getAttribute("qnaSDoc");
            List<SkillSeedExample> seed_examples = qnaDoc.getSeed_examples();
            SkillSeedExample example = null;
            Iterator<SkillSeedExample> it = null;
            it = seed_examples.iterator();
            String divVisibility = "";
            String yaml = (String)request.getAttribute("yaml");
            boolean preview = (yaml != null);
            out.print("  <head>\n");
            out.print("  <meta charset=\"UTF-8\">\n");
            out.print("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
            out.print("  <title>Q&A Wizard</title>\n");
            out.print("  <link rel=\"stylesheet\" href=\"css/grid-layout.css\" />\n");
            out.print("  <link rel=\"stylesheet\" href=\"css/style.css\" />\n");
            out.print("</head>\n");
            out.print("  <body>\n");
            out.print("     <header>\n");
            out.print("      <div class=\"row\" style=\"height:10vh;\">        \n");
            out.print("      </div><!-- End row-->\n");
            out.print("    </header>\n");
            out.print("    <main>\n");
            out.print("      <div class=\"row\"><!-- start div \"row\" -->\n");
            out.print("        <div class=\"three\">&nbsp;</div> \n");
            out.print("        <form method=\"post\" action=\"/filesave\" >\n");
            out.print(" ");
            if(preview)
            {
            out.print("          <div id=\"yaml\" class=\"mainpage\" style=\"height:45%; width:50%; margin: auto; padding: 2%;\">\n");
            out.print("            <div class=\"six\"> \n");
            out.print("              &nbsp;\n");
            out.print("            </div>\n");
            out.print("            <div class=\"six\" align=\"right\">\n");
            out.print("              <a onclick=\"document.getElementById('yaml').style.display = 'none'; document.getElementById('metadata').style.display = 'block'; document.getElementById('seed_examples').style.display = 'none';\" class=\"x\">❌</a>\n");
            out.print("            </div>\n");
            out.print("            <br /><br />\n");
            out.print("            <h1>Knowledge Q&A</h1>\n");
            out.print("            <br /><br />\n");
            out.print("            <h2>YAML Preview:</h2><br />\n");
            out.print("            <div style=\"border: 2px solid darkgray; margin: auto; padding: 2%; border-radius: 10px; background-color: rgb(200, 197, 197);\">\n");
            out.print("              <div> \n");
            out.print("                <font style=\"font-size: 17px; line-height: 1.27;\">\n");
            out.print(" ");
            out.print(yaml);
            out.print("                </font>\n");
            out.print("              </div>\n");
            out.print("            </div>\n");
            out.print("          </div>\n");
            out.print(" ");
            divVisibility="display: none;";
            }
            out.print("          <div id=\"metadata\" class=\"mainpage\" style=\"height:45%; width:50%; margin: auto; padding: 2%; ");
            out.print(divVisibility);
            out.print("\"> <!-- start div \"mainpage\" -->\n");
            out.print("            <div class=\"row\">\n");
            out.print("              <div class=\"six\">&nbsp;</div>\n");
            out.print("              <div class=\"six\" align=\"right\" vertical-align=\"top\">\n");
            out.print("                <img src=\"images/ai.png\" height=\"70px\" style=\"vertical-align:top;\">\n");
            out.print("              </div>\n");
            out.print("            </div>\n");
            out.print("            <br /><br />\n");
            out.print("            <h1>Skill Q&A</h1>\n");
            out.print("            <div align=\"right\" vertical-align=\"top\" style=\"margin: auto; padding: 2%; border-radius: 10px;\">\n");
            out.print("              <input type=\"submit\" name=\"save\" value=\"Save\" style=\"background-image: url(images/save.png);\" class=\"my-submit-icon\"> | \n");
            out.print("              <input type=\"submit\" name=\"export\" value=\"Export\" style=\"background-image: url(images/download.png);\" class=\"my-submit-icon\"> | \n");
            out.print("              <input type=\"submit\" name=\"preview\" value=\"YAML Preview\" style=\"background-image: url(images/preview.png);\" class=\"my-submit-icon\">\n");
            out.print("            </div>\n");
            out.print("            <h2>Metadata:</h2><br />\n");
            out.print("            <div style=\"border: 2px solid darkgray;; margin: auto; padding: 2%; border-radius: 10px;\"> <!-- start div \"border\" -->\n");
            out.print("              <div class=\"four\"> <!-- start div \"four\" -->\n");
            out.print("                <label for=\"created_by\">Created by: </label><br /><input type=\"text\" id=\"created_by\" name=\"created_by\" style=\"width: 50%;\" title=\"Your GitHub username.\" value=\"");
            out.print(qnaDoc.getCreated_by());
            out.print("\">\n");
            out.print("                <input type=\"hidden\" id=\"version\" name=\"version\" value=\"2\">\n");
            out.print("              </div> <!-- end div \"four\" -->\n");
            out.print("              <div> <!-- start div \"eight\" -->\n");
            out.print("                <br /><label for=\"task_description\">Task Description: </label><br />\n");
            out.print("                <input type=\"text\" id=\"task_description\" name=\"task_description\" style=\"width: 61%;\" title=\"A description of the skill.\" value=\"");
            out.print(qnaDoc.getTask_description());
            out.print("\"><br />\n");
            out.print("              </div> <!-- end div \"eight\" -->\n");
            out.print("            </div><!-- end div \"border\" -->\n");
            out.print("            <br /><br />\n");
            out.print("            <h2>Seed Examples:</h2><br />\n");
            out.print(" ");
            int exampleCount = 0;
            while(it.hasNext())
            {
            example = it.next();
            out.print("            <div style=\"border: 2px solid darkgray; margin: auto; padding: 2%; border-radius: 10px;\"> <!-- start div \"border\" -->\n");
            out.print("              <div> <!-- start div -->\n");
            out.print("                <br /><label for=\"context\" style=\"font-weight: bold;\">Context:<br /></label> <textarea id=\"context_");
            out.print(exampleCount);
            out.print("\" name=\"context_");
            out.print(exampleCount);
            out.print("\" rows=\"7\" style=\"width: 90%;\" title=\"Grounded skills require the user to provide context containing information that the model is expected to take into account during processing. This is different from knowledge, where the model is expected to gain facts and background knowledge from the tuning process. The context key should not be used for ungrounded skills.\">");
            out.print(example.getContext());
            out.print("</textarea>\n");
            out.print("              </div> <!-- end div -->\n");
            out.print("              <div style=\"width:100%;\"> <!-- start div \"100%\" -->\n");
            out.print("                <br /><br /><h2>Questions and Answers:</h2><br /><hr  style=\"width:97%;\"/><br />\n");
            out.print("                <label for=\"question_");
            out.print(exampleCount);
            out.print("\">Question: </label><br /><input type=\"text\" id=\"question_");
            out.print(exampleCount);
            out.print("\" name=\"question_");
            out.print(exampleCount);
            out.print("\" style=\"width: 90%;\" title=\"A question for the model.\" value=\"");
            out.print(example.getQuestion());
            out.print("\"><br />\n");
            out.print("                <label for=\"answer_");
            out.print(exampleCount);
            out.print("\">Answer: </label><br /><textarea id=\"answer_");
            out.print(exampleCount);
            out.print("\" name=\"answer_");
            out.print(exampleCount);
            out.print("\" rows=\"2\" style=\"width: 90%;\" title=\"The desired response from the model.\">");
            out.print(example.getAnswer());
            out.print("</textarea><br />\n");
            out.print("              </div> <!-- end div \"100%\" -->\n");
            out.print("            </div> <!-- end div \"border\" -->\n");
            out.print("            <br /><br />\n");
            out.print(" ");
            exampleCount++;
            }
            out.print("          </div> <!-- end div \"mainpage\" -->\n");
            out.print("        </form>\n");
            out.print("      </div><!-- End row-->\n");
            out.print("    </main>\n");
            out.print("    <footer>\n");
            out.print("    </footer>\n");
            out.print("  </body>\n");
            out.print("</html>\n");
          }
        catch(Exception ex)
          {
            if(errorPage != null)
              {
                request.setAttribute("jakarta.servlet.error.exception", ex);
            
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(errorPage);
                dispatcher.forward(request, response);
              }
            else
              throw new ServletException(ex);
          }
      }
  }