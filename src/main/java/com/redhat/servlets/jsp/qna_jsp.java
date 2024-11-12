package com.redhat.servlets.jsp;

import com.redhat.rad.yaml.model.QnA;
import com.redhat.rad.yaml.model.SeedExample;
import com.redhat.rad.yaml.model.SeedQandA;
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
            QnA qnaDoc = (QnA)session.getAttribute("qnaDoc");
            List<String> patterns = qnaDoc.getDocument().getPatterns();
            String patternList = "";
            Iterator it = patterns.iterator();
            while(it.hasNext())
            patternList += "," + (String)it.next();
            patternList = patternList.substring(1);
            List<SeedExample> seed_examples = qnaDoc.getSeed_examples();
            SeedExample example = null;
            SeedQandA qna = null;
            it = seed_examples.iterator();
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
            out.print("      <div class=\"row\">\n");
            out.print("        <div class=\"three\">&nbsp;</div>\n");
            out.print("        <div id=\"metadata\" class=\"mainpage\" style=\"height:45%; width:50%; margin: auto; padding: 2%;\">\n");
            out.print("          <font style=\"font-size: 16px;\">Metadata | <a href=\"#\" onclick=\"document.getElementById('metadata').style.display = 'none'; document.getElementById('seed_examples').style.display = 'block';\">Seed Examples</a></font><br /><br />\n");
            out.print("          <h1>");
            out.print(("3".equals(qnaDoc.getVersion()))?"Knowledge":"Skill");
            out.print(" Q&A: Metadata</h1>\n");
            out.print("          <br /><br />\n");
            out.print("          <form >\n");
            out.print("            <div style=\"border: 2px solid darkgray;; margin: auto; padding: 2%; border-radius: 10px;\">\n");
            out.print("            <div class=\"four\"> \n");
            out.print("              <label for=\"domain\">Domain: </label> <input type=\"text\" id=\"domain\" name=\"domain\" style=\"width: 50%;\" title=\"Specify the category of the knowledge.\" value=\"");
            out.print(qnaDoc.getDomain());
            out.print("\"><br />\n");
            out.print("              <label for=\"created_by\">Created by: </label> <input type=\"text\" id=\"created_by\" name=\"created_by\" style=\"width: 50%;\" title=\"Your GitHub username.\" value=\"");
            out.print(qnaDoc.getCreated_by());
            out.print("\">\n");
            out.print("              <input type=\"hidden\" id=\"version\" name=\"version\" value=\"");
            out.print(qnaDoc.getVersion());
            out.print("\">\n");
            out.print("            </div>\n");
            out.print("            <div class=\"eight>\"\n");
            out.print("              <br /><label for=\"outline\">Outline:<br /></label> <textarea id=\"outline\" name=\"outline\" style=\"width: 61%;\" title=\"Describe an overview of the document your submitting.\">");
            out.print(qnaDoc.getDocument_outline());
            out.print("</textarea>\n");
            out.print("            </div>\n");
            out.print("            <div style=\"width:100%;\">\n");
            out.print("              <br /><br /><h2>Source Content:</h2><br /><hr  style=\"width:97%;\"/><br />\n");
            out.print("              <label for=\"repo\">Git URL: </label> <input type=\"text\" id=\"repo\" name=\"repo\" style=\"width: 60%;\" title=\"The URL to your repository that holds your knowledge markdown files.\" value=\"");
            out.print(qnaDoc.getDocument().getRepo());
            out.print("\"><br />\n");
            out.print("              <label for=\"commit\">Commit ref: </label> <input type=\"text\" id=\"commit\" name=\"commit\"  style=\"width: 40%;\" title=\"The SHA of the commit in your repository with your knowledge markdown files.\" value=\"");
            out.print(qnaDoc.getDocument().getCommit());
            out.print("\"><br />\n");
            out.print("              <label for=\"patterns\">Patterns: </label> <input type=\"text\" id=\"patterns\" name=\"patterns\"  style=\"width: 40%;\" title='A list of glob patterns specifying the markdown files in your repository. Any glob pattern that starts with *, such as *.md, must be quoted due to YAML rules. For example, \"*.md\".' value=\"");
            out.print(patternList);
            out.print("\"><br /><br />\n");
            out.print("            </div>\n");
            out.print("           </div>\n");
            out.print("          </form>\n");
            out.print("        </div>\n");
            out.print("        <div id=\"seed_examples\" class=\"mainpage\" style=\"height:45%; width:50%; margin: auto; padding: 2%; display: none\">\n");
            out.print("          <font style=\"font-size: 16px;\"><a href=\"#\" onclick=\"document.getElementById('seed_examples').style.display = 'none'; document.getElementById('metadata').style.display = 'block'\">Metadata</a> | Seed Examples</font><br /><br />\n");
            out.print("          <h1>");
            out.print(("3".equals(qnaDoc.getVersion()))?"Knowledge":"Skill");
            out.print(" Q&A: Seed Examples</h1>\n");
            out.print("          <br /><br />\n");
            out.print("          <form >\n");
            out.print(" ");
            while(it.hasNext())
            {
            example = (SeedExample)it.next();
            List<SeedQandA> qnaList = example.getQuestions_and_answers();
            Iterator qnaIt = qnaList.iterator();
            out.print("            <div style=\"border: 2px solid darkgray;; margin: auto; padding: 2%; border-radius: 10px;\">\n");
            out.print("            <div class=\"eleven>\"\n");
            out.print("              <br /><label for=\"context\" style=\"font-weight: bold;\">Context:<br /></label> <textarea id=\"context\" name=\"context\" rows=\"7\" style=\"width: 90%;\" title=\"A chunk of information from the knowledge document. Each qna.yaml needs five context blocks and has a maximum word count of 500 words.\">");
            out.print(example.getContext());
            out.print("</textarea>\n");
            out.print("            </div>\n");
            out.print("            <div style=\"width:100%;\">\n");
            out.print("              <br /><br /><h2>Questions and Answers:</h2><br /><hr  style=\"width:97%;\"/><br />\n");
            out.print(" ");
            while(qnaIt.hasNext())
            {
            qna = (SeedQandA)qnaIt.next();
            out.print("              <label for=\"question\">Question: </label><br /><input type=\"text\" id=\"question\" name=\"question\" style=\"width: 90%;\" title=\"Specify a question for the model. Each qna.yaml file needs at least three question and answer pairs per context chunk with a maximum word count of 250 words.\" value=\"");
            out.print(qna.getQuestion());
            out.print("\"><br />\n");
            out.print("              <label for=\"answer\">Answer: </label><br /><textarea id=\"context\" name=\"context\" rows=\"2\" style=\"width: 90%;\" title=\"Specify the desired answer from the model. Each qna.yaml file needs at least three question and answer pairs per context chunk with a maximum word count of 250 words.\">");
            out.print(qna.getAnswer());
            out.print("</textarea><br />\n");
            out.print(" ");
            }
            out.print("            </div>\n");
            out.print("          </div>\n");
            out.print(" ");
            }
            out.print("          </form>\n");
            out.print("        </div>\n");
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