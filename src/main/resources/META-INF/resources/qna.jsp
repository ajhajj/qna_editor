<!DOCTYPE html>
<html lang="en">
  <%@page import="com.redhat.rag.yaml.model.KnowledgeQnA,
                  com.redhat.rag.yaml.model.KnowledgeSeedExample,
                  com.redhat.rag.yaml.model.KnowledgeSeedQandA,
                  java.util.List,
                  java.util.Iterator" session="true"%>
  <%
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
  %>

  <head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Q&A Wizard</title>
  <link rel="icon" type="image/x-icon" href="/images/favicon.ico">
  <link rel="stylesheet" href="css/grid-layout.css" />
  <link rel="stylesheet" href="css/style.css" />

</head>

  <body>
     <header>
      <div class="row" style="height:10vh;">        
      </div><!-- End row-->
    </header>
    <main>
      <div class="row">
        <div class="three">&nbsp;</div>
        <form method="post" action="/filesave" >
          <%
            if(preview)
              {
          %>
          <div id="yaml" class="mainpage" style="height:45%; width:50%; margin: auto; padding: 2%;">
            <div class="six"> 
              &nbsp;
            </div>
            <div class="six" align="right">
              <a onclick="document.getElementById('yaml').style.display = 'none'; document.getElementById('metadata').style.display = 'block'; document.getElementById('seed_examples').style.display = 'none';" class="x">❌</a>
            </div>
            <br /><br />
            <h1>Knowledge Q&A</h1>
            <br /><br />
            <h2>YAML Preview:</h2><br />
            <div style="border: 2px solid darkgray; margin: auto; padding: 2%; border-radius: 10px; background-color: rgb(200, 197, 197);">
              <div> 
                <font style="font-size: 17px; line-height: 1.27;">
                <%=yaml %>
                </font>
              </div>
            </div>
          </div>
          <%
                divVisibility="display: none;";
              }
          %>

          
          <div id="metadata" class="mainpage" style="height:45%; width:50%; margin: auto; padding: 2%; <%=divVisibility %>">
            <div class="row">
              <div class="six"> 
                <font style="font-size: 16px;">Metadata | <a href="#" onclick="document.getElementById('metadata').style.display = 'none'; document.getElementById('seed_examples').style.display = 'block';">Seed Examples</a></font>
              </div>
              <div class="six" align="right" vertical-align="top">
                <img src="images/ai.png" height="70px" style="vertical-align:top;">
              </div>
            </div>
            <br /><br />
            <h1>Knowledge Q&A</h1>
            <br /><br />
            <h2>Metadata:</h2>
            <div align="right" vertical-align="top" style="margin: auto; padding: 2%; border-radius: 10px;">
              <input type="button" name="home" value="Home" title="Home" style="background-image: url(images/home.png);" class="my-submit-icon" onclick="location.href='/';"> | 
              <input type="submit" name="save" value="Save" title="Save" style="background-image: url(images/save.png);" class="my-submit-icon"> | 
              <input type="submit" name="export" value="Export" title="Export YAML" style="background-image: url(images/download.png);" class="my-submit-icon"> | 
              <input type="submit" name="preview" value="YAML Preview" title="Preview YAML" style="background-image: url(images/preview.png);" class="my-submit-icon">
            </div>
            <div style="border: 2px solid darkgray; margin: auto; padding: 2%; border-radius: 10px;">
              <div class="four"> 
                <label for="domain">Domain: </label> <input type="text" id="domain" name="domain" style="width: 50%;" title="Specify the category of the knowledge." value="<%=qnaDoc.getDomain() %>"><br />
                <label for="created_by">Created by: </label> <input type="text" id="created_by" name="created_by" style="width: 50%;" title="Your GitHub username." value="<%=qnaDoc.getCreated_by() %>">
                <input type="hidden" id="version" name="version" value="<%=qnaDoc.getVersion() %>">
              </div>
              <div class="eight>"
                <br /><label for="outline">Outline:<br /></label> <textarea id="outline" name="outline" style="width: 61%;" title="Describe an overview of the document your submitting."><%=qnaDoc.getDocument_outline() %></textarea>
              </div>
              <div style="width:100%;">
                <br /><br /><h2>Source Content:</h2><br /><hr  style="width:97%;"/><br />
                <label for="repo">Git URL: </label> <input type="text" id="repo" name="repo" style="width: 60%;" title="The URL to your repository that holds your knowledge markdown files." value="<%=qnaDoc.getDocument().getRepo() %>"><br />
                <label for="commit">Commit ref: </label> <input type="text" id="commit" name="commit"  style="width: 40%;" title="The SHA of the commit in your repository with your knowledge markdown files." value="<%=qnaDoc.getDocument().getCommit() %>"><br />
                <label for="patterns">Patterns: </label> <input type="text" id="patterns" name="patterns"  style="width: 40%;" title='A list of glob patterns specifying the markdown files in your repository. Any glob pattern that starts with *, such as *.md, must be quoted due to YAML rules. For example, "*.md".' value="<%=patternList %>"><br /><br />
              </div>
            </div>
          </div>


          <div id="seed_examples" class="mainpage" style="height:45%; width:50%; margin: auto; padding: 2%; display: none">
            <div class="row">
              <div class="six"> 
                <font style="font-size: 16px;"><a href="#" onclick="document.getElementById('seed_examples').style.display = 'none'; document.getElementById('metadata').style.display = 'block'">Metadata</a> | Seed Examples</font>
              </div>
              <div class="six" align="right" vertical-align="top">
                <img src="images/ai.png" height="70px" style="vertical-align:top;">
              </div>
            </div>
            <br /><br />
            <h1>Knowledge Q&A</h1>
            <br /><br />
            <h2>Seed Examples:</h2>
            <div align="right" vertical-align="top" style="margin: auto; padding: 2%; border-radius: 10px;">
              <input type="button" name="home" value="Home" title="Home" style="background-image: url(images/home.png);" class="my-submit-icon" onclick="location.href='/';"> | 
              <input type="submit" name="save" value="Save" title="Save" style="background-image: url(images/save.png);" class="my-submit-icon"> | 
              <input type="submit" name="export" value="Export" title="Export YAML" style="background-image: url(images/download.png);" class="my-submit-icon"> | 
              <input type="submit" name="preview" value="YAML Preview" title="Preview YAML" style="background-image: url(images/preview.png);" class="my-submit-icon">
            </div>
            <% 
              int exampleCount = 0;
              int qnaCount = 0;
              while(it.hasNext()) 
                {
                  example = it.next();
                  List<KnowledgeSeedQandA> qnaList = example.getQuestions_and_answers();
                  Iterator<KnowledgeSeedQandA> qnaIt = qnaList.iterator();

                  if(exampleCount > 0)
            %>
            <br />
            <div style="border: 2px solid darkgray; margin: auto; padding: 2%; border-radius: 10px;">
              <div class="eleven>"
                <br /><label for="context" style="font-weight: bold;">Context:<br /></label> <textarea id="context_<%=exampleCount%>" name="context_<%=exampleCount%>" rows="7" style="width: 90%;" title="A chunk of information from the knowledge document. Each qna.yaml needs five context blocks and has a maximum word count of 500 words."><%=example.getContext() %></textarea>
              </div>
              <div style="width:100%;">
                <br /><br /><h2>Questions and Answers:</h2><br /><hr  style="width:97%;"/><br />
                <%
                qnaCount = 0;
                while(qnaIt.hasNext())
                  {
                    qna = qnaIt.next();
                %>
                <label for="question_<%=exampleCount%>_<%=qnaCount%>">Question: </label><br /><input type="text" id="question_<%=exampleCount%>_<%=qnaCount%>" name="question_<%=exampleCount%>_<%=qnaCount%>" style="width: 90%;" title="Specify a question for the model. Each qna.yaml file needs at least three question and answer pairs per context chunk with a maximum word count of 250 words." value="<%=qna.getQuestion() %>"><br />
                <label for="answer_<%=exampleCount%>_<%=qnaCount%>">Answer: </label><br /><textarea id="answer_<%=exampleCount%>_<%=qnaCount%>" name="answer_<%=exampleCount%>_<%=qnaCount%>" rows="2" style="width: 90%;" title="Specify the desired answer from the model. Each qna.yaml file needs at least three question and answer pairs per context chunk with a maximum word count of 250 words."><%=qna.getAnswer() %></textarea><br />
                <%
                    qnaCount++;  
                  } 
                %>
              </div>
            </div>
            <%
                exampleCount++;
              }
            %>
          </div>
        </form>
      </div><!-- End row-->
  
    </main>
    <footer>
    </footer>
  </body>
</html>