package com.redhat.servlets.jsp;

import com.redhat.rad.yaml.model.KnowledgeQnA;
import com.redhat.rad.yaml.model.KnowledgeSeedExample;
import com.redhat.rad.yaml.model.KnowledgeSeedQandA;
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
    name = "qna_jsp",
    urlPatterns = {"/qna_jsp"}
  )
public class qna_jsp extends HttpServlet //initial
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
            KnowledgeQnA qnaDoc = (KnowledgeQnA)session.getAttribute("qnaDoc");
            List<String> patterns = qnaDoc.getDocument().getPatterns();
            String patternList = "";
            Iterator<String> iter = patterns.iterator();
            while(iter.hasNext())
            patternList += "," + iter.next();
            patternList = patternList.substring(1);
            List<KnowledgeSeedExample> seed_examples = qnaDoc.getSeed_examples();
            KnowledgeSeedExample example = null;
            KnowledgeSeedQandA qna = null;
            Iterator<KnowledgeSeedExample> it = null;
            it = seed_examples.iterator();
            String divVisibility = "";
            String yaml = (String)request.getAttribute("yaml");
            boolean preview = (yaml != null);
            out.print("  <head>\n");
            out.print("  <meta charset=\"UTF-8\">\n");
            out.print("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
            out.print("  <title>Q&A Wizard</title>\n");
            out.print("  <link rel=\"icon\" type=\"image/x-icon\" href=\"/images/favicon.ico\">\n");
            out.print("  <link rel=\"stylesheet\" href=\"css/grid-layout.css\" />\n");
            out.print("  <link rel=\"stylesheet\" href=\"css/style.css\" />\n");
            out.print("</head>\n");
            out.print("  <body>\n");
            out.print("     <header>\n");
            out.print("      <div class=\"row\" style=\"height:10vh;\">        \n");
            out.print("      </div><!-- End row-->\n");
            out.print("    </header>\n");
            out.print("    <main>\n");
            out.print("      <div class=\"row\">\n");
            out.print("        <div class=\"three\">&nbsp;</div>\n");
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
            out.print("\">\n");
            out.print("            <div class=\"row\">\n");
            out.print("              <div class=\"six\"> \n");
            out.print("                <font style=\"font-size: 16px;\">Metadata | <a href=\"#\" onclick=\"document.getElementById('metadata').style.display = 'none'; document.getElementById('seed_examples').style.display = 'block';\">Seed Examples</a></font>\n");
            out.print("              </div>\n");
            out.print("              <div class=\"six\" align=\"right\" vertical-align=\"top\">\n");
            out.print("                <img src=\"images/ai.png\" height=\"70px\" style=\"vertical-align:top;\">\n");
            out.print("              </div>\n");
            out.print("            </div>\n");
            out.print("            <br /><br />\n");
            out.print("            <h1>Knowledge Q&A</h1>\n");
            out.print("            <br /><br />\n");
            out.print("            <h2>Metadata:</h2>\n");
            out.print("            <div align=\"right\" vertical-align=\"top\" style=\"margin: auto; padding: 2%; border-radius: 10px;\">\n");
            out.print("              <input type=\"submit\" name=\"save\" value=\"Save\" style=\"background-image: url(images/save.png);\" class=\"my-submit-icon\"> | \n");
            out.print("              <input type=\"submit\" name=\"export\" value=\"Export\" style=\"background-image: url(images/download.png);\" class=\"my-submit-icon\"> | \n");
            out.print("              <input type=\"submit\" name=\"preview\" value=\"YAML Preview\" style=\"background-image: url(images/preview.png);\" class=\"my-submit-icon\">\n");
            out.print("            </div>\n");
            out.print("            <div style=\"border: 2px solid darkgray; margin: auto; padding: 2%; border-radius: 10px;\">\n");
            out.print("              <div class=\"four\"> \n");
            out.print("                <label for=\"domain\">Domain: </label> <input type=\"text\" id=\"domain\" name=\"domain\" style=\"width: 50%;\" title=\"Specify the category of the knowledge.\" value=\"");
            out.print(qnaDoc.getDomain());
            out.print("\"><br />\n");
            out.print("                <label for=\"created_by\">Created by: </label> <input type=\"text\" id=\"created_by\" name=\"created_by\" style=\"width: 50%;\" title=\"Your GitHub username.\" value=\"");
            out.print(qnaDoc.getCreated_by());
            out.print("\">\n");
            out.print("                <input type=\"hidden\" id=\"version\" name=\"version\" value=\"");
            out.print(qnaDoc.getVersion());
            out.print("\">\n");
            out.print("              </div>\n");
            out.print("              <div class=\"eight>\"\n");
            out.print("                <br /><label for=\"outline\">Outline:<br /></label> <textarea id=\"outline\" name=\"outline\" style=\"width: 61%;\" title=\"Describe an overview of the document your submitting.\">");
            out.print(qnaDoc.getDocument_outline());
            out.print("</textarea>\n");
            out.print("              </div>\n");
            out.print("              <div style=\"width:100%;\">\n");
            out.print("                <br /><br /><h2>Source Content:</h2><br /><hr  style=\"width:97%;\"/><br />\n");
            out.print("                <label for=\"repo\">Git URL: </label> <input type=\"text\" id=\"repo\" name=\"repo\" style=\"width: 60%;\" title=\"The URL to your repository that holds your knowledge markdown files.\" value=\"");
            out.print(qnaDoc.getDocument().getRepo());
            out.print("\"><br />\n");
            out.print("                <label for=\"commit\">Commit ref: </label> <input type=\"text\" id=\"commit\" name=\"commit\"  style=\"width: 40%;\" title=\"The SHA of the commit in your repository with your knowledge markdown files.\" value=\"");
            out.print(qnaDoc.getDocument().getCommit());
            out.print("\"><br />\n");
            out.print("                <label for=\"patterns\">Patterns: </label> <input type=\"text\" id=\"patterns\" name=\"patterns\"  style=\"width: 40%;\" title='A list of glob patterns specifying the markdown files in your repository. Any glob pattern that starts with *, such as *.md, must be quoted due to YAML rules. For example, \"*.md\".' value=\"");
            out.print(patternList);
            out.print("\"><br /><br />\n");
            out.print("              </div>\n");
            out.print("            </div>\n");
            out.print("          </div>\n");
            out.print("          <div id=\"seed_examples\" class=\"mainpage\" style=\"height:45%; width:50%; margin: auto; padding: 2%; display: none\">\n");
            out.print("            <div class=\"row\">\n");
            out.print("              <div class=\"six\"> \n");
            out.print("                <font style=\"font-size: 16px;\"><a href=\"#\" onclick=\"document.getElementById('seed_examples').style.display = 'none'; document.getElementById('metadata').style.display = 'block'\">Metadata</a> | Seed Examples</font>\n");
            out.print("              </div>\n");
            out.print("              <div class=\"six\" align=\"right\" vertical-align=\"top\">\n");
            out.print("                <img src=\"images/ai.png\" height=\"70px\" style=\"vertical-align:top;\">\n");
            out.print("              </div>\n");
            out.print("            </div>\n");
            out.print("            <br /><br />\n");
            out.print("            <h1>Knowledge Q&A</h1>\n");
            out.print("            <br /><br />\n");
            out.print("            <h2>Seed Examples:</h2>\n");
            out.print("            <div align=\"right\" vertical-align=\"top\" style=\"margin: auto; padding: 2%; border-radius: 10px;\">\n");
            out.print("              <input type=\"submit\" name=\"save\" value=\"Save\" style=\"background-image: url(images/save.png);\" class=\"my-submit-icon\"> | \n");
            out.print("              <input type=\"submit\" name=\"export\" value=\"Export\" style=\"background-image: url(images/download.png);\" class=\"my-submit-icon\"> | \n");
            out.print("              <input type=\"submit\" name=\"preview\" value=\"YAML Preview\" style=\"background-image: url(images/preview.png);\" class=\"my-submit-icon\">\n");
            out.print("            </div>\n");
            out.print(" ");
            int exampleCount = 0;
            int qnaCount = 0;
            while(it.hasNext())
            {
            example = it.next();
            List<KnowledgeSeedQandA> qnaList = example.getQuestions_and_answers();
            Iterator<KnowledgeSeedQandA> qnaIt = qnaList.iterator();
            if(exampleCount > 0)
            out.print("            <br />\n");
            out.print("            <div style=\"border: 2px solid darkgray; margin: auto; padding: 2%; border-radius: 10px;\">\n");
            out.print("              <div class=\"eleven>\"\n");
            out.print("                <br /><label for=\"context\" style=\"font-weight: bold;\">Context:<br /></label> <textarea id=\"context_");
            out.print(exampleCount);
            out.print("\" name=\"context_");
            out.print(exampleCount);
            out.print("\" rows=\"7\" style=\"width: 90%;\" title=\"A chunk of information from the knowledge document. Each qna.yaml needs five context blocks and has a maximum word count of 500 words.\">");
            out.print(example.getContext());
            out.print("</textarea>\n");
            out.print("              </div>\n");
            out.print("              <div style=\"width:100%;\">\n");
            out.print("                <br /><br /><h2>Questions and Answers:</h2><br /><hr  style=\"width:97%;\"/><br />\n");
            out.print(" ");
            qnaCount = 0;
            while(qnaIt.hasNext())
            {
            qna = qnaIt.next();
            out.print("                <label for=\"question_");
            out.print(exampleCount);
            out.print("_");
            out.print(qnaCount);
            out.print("\">Question: </label><br /><input type=\"text\" id=\"question_");
            out.print(exampleCount);
            out.print("_");
            out.print(qnaCount);
            out.print("\" name=\"question_");
            out.print(exampleCount);
            out.print("_");
            out.print(qnaCount);
            out.print("\" style=\"width: 90%;\" title=\"Specify a question for the model. Each qna.yaml file needs at least three question and answer pairs per context chunk with a maximum word count of 250 words.\" value=\"");
            out.print(qna.getQuestion());
            out.print("\"><br />\n");
            out.print("                <label for=\"answer_");
            out.print(exampleCount);
            out.print("_");
            out.print(qnaCount);
            out.print("\">Answer: </label><br /><textarea id=\"answer_");
            out.print(exampleCount);
            out.print("_");
            out.print(qnaCount);
            out.print("\" name=\"answer_");
            out.print(exampleCount);
            out.print("_");
            out.print(qnaCount);
            out.print("\" rows=\"2\" style=\"width: 90%;\" title=\"Specify the desired answer from the model. Each qna.yaml file needs at least three question and answer pairs per context chunk with a maximum word count of 250 words.\">");
            out.print(qna.getAnswer());
            out.print("</textarea><br />\n");
            out.print(" ");
            qnaCount++;
            }
            out.print("              </div>\n");
            out.print("            </div>\n");
            out.print(" ");
            exampleCount++;
            }
            out.print("          </div>\n");
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