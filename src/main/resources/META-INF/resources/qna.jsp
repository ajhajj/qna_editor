<!DOCTYPE html>
<html lang="en">
  <%@page import="com.redhat.rad.yaml.model.QnA,
                  com.redhat.rad.yaml.model.SeedExample,
                  com.redhat.rad.yaml.model.SeedQandA,
                  java.util.List,
                  java.util.Iterator" session="true"%>
  <%
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
      
  %>

  <head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Q&A Wizard</title>
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
        <div id="metadata" class="mainpage" style="height:45%; width:50%; margin: auto; padding: 2%;">
          <font style="font-size: 16px;">Metadata | <a href="#" onclick="document.getElementById('metadata').style.display = 'none'; document.getElementById('seed_examples').style.display = 'block';">Seed Examples</a></font><br /><br />
          <h1><%=("3".equals(qnaDoc.getVersion()))?"Knowledge":"Skill" %> Q&A: Metadata</h1>
          <br /><br />
          <form >
            <div style="border: 2px solid darkgray;; margin: auto; padding: 2%; border-radius: 10px;">
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
          </form>
        </div>


        <div id="seed_examples" class="mainpage" style="height:45%; width:50%; margin: auto; padding: 2%; display: none">
          <font style="font-size: 16px;"><a href="#" onclick="document.getElementById('seed_examples').style.display = 'none'; document.getElementById('metadata').style.display = 'block'">Metadata</a> | Seed Examples</font><br /><br />
          <h1><%=("3".equals(qnaDoc.getVersion()))?"Knowledge":"Skill" %> Q&A: Seed Examples</h1>
          <br /><br />
          <form >
            <% 
              while(it.hasNext()) 
                {
                  example = (SeedExample)it.next();
                  List<SeedQandA> qnaList = example.getQuestions_and_answers();
                  Iterator qnaIt = qnaList.iterator();
            %>
            <div style="border: 2px solid darkgray;; margin: auto; padding: 2%; border-radius: 10px;">
            <div class="eleven>"
              <br /><label for="context" style="font-weight: bold;">Context:<br /></label> <textarea id="context" name="context" rows="7" style="width: 90%;" title="A chunk of information from the knowledge document. Each qna.yaml needs five context blocks and has a maximum word count of 500 words."><%=example.getContext() %></textarea>
            </div>
            <div style="width:100%;">
              <br /><br /><h2>Questions and Answers:</h2><br /><hr  style="width:97%;"/><br />
              <%
                while(qnaIt.hasNext())
                  {
                    qna = (SeedQandA)qnaIt.next();
              %>
              <label for="question">Question: </label><br /><input type="text" id="question" name="question" style="width: 90%;" title="Specify a question for the model. Each qna.yaml file needs at least three question and answer pairs per context chunk with a maximum word count of 250 words." value="<%=qna.getQuestion() %>"><br />
              <label for="answer">Answer: </label><br /><textarea id="context" name="context" rows="2" style="width: 90%;" title="Specify the desired answer from the model. Each qna.yaml file needs at least three question and answer pairs per context chunk with a maximum word count of 250 words."><%=qna.getAnswer() %></textarea><br />
              <%  } %>
            </div>
          </div>
            <%
                }
            %>
          </form>
        </div>
      </div><!-- End row-->
    </main>
    <footer>
    </footer>
  </body>

</html>