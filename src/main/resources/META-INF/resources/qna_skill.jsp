<!DOCTYPE html>
<html lang="en">
  <%@page import="com.redhat.rad.yaml.model.SkillQnA,
                  com.redhat.rad.yaml.model.SkillSeedExample,
                  java.util.List,
                  java.util.Iterator" session="true"%>
  <%
    SkillQnA qnaDoc = (SkillQnA)session.getAttribute("qnaSDoc");
    List<SkillSeedExample> seed_examples = qnaDoc.getSeed_examples();
    SkillSeedExample example = null;
    Iterator<SkillSeedExample> it = null;
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
      <div class="row"><!-- start div "row" -->
        <div class="three">&nbsp;</div> 
        <form method="post" action="/filesave" >
          <div id="metadata" class="mainpage" style="height:45%; width:50%; margin: auto; padding: 2%;"> <!-- start div "mainpage" -->
            <div class="six">&nbsp;</div>
            <div class="six" align="right"> <!-- start div "six" -->
              <input type="submit" name="save" value="Save"> <input type="submit" name="export" value="Export">
            </div> <!-- end div "six" -->
            <br /><br />
            <h1>Skill Q&A</h1>
            <br /><br />
            <h2>Metadata:</h2><br />
            <div style="border: 2px solid darkgray;; margin: auto; padding: 2%; border-radius: 10px;"> <!-- start div "border" -->
              <div class="four"> <!-- start div "four" -->
                <label for="created_by">Created by: </label><br /><input type="text" id="created_by" name="created_by" style="width: 50%;" title="Your GitHub username." value="<%=qnaDoc.getCreated_by() %>">
                <input type="hidden" id="version" name="version" value="2">
              </div> <!-- end div "four" -->
              <div> <!-- start div "eight" -->
                <br /><label for="task_description">Task Description: </label><br />
                <input type="text" id="task_description" name="task_description" style="width: 61%;" title="A description of the skill." value="<%=qnaDoc.getTask_description() %>"><br />
              </div> <!-- end div "eight" -->
            </div><!-- end div "border" -->
            <br /><br />
            <h2>Seed Examples:</h2><br />
            <% 
              int exampleCount = 0;
              while(it.hasNext()) 
                {
                  example = it.next();
            %>
            <div style="border: 2px solid darkgray; margin: auto; padding: 2%; border-radius: 10px;"> <!-- start div "border" -->
              <div> <!-- start div -->
                <br /><label for="context" style="font-weight: bold;">Context:<br /></label> <textarea id="context_<%=exampleCount%>" name="context_<%=exampleCount%>" rows="7" style="width: 90%;" title="Grounded skills require the user to provide context containing information that the model is expected to take into account during processing. This is different from knowledge, where the model is expected to gain facts and background knowledge from the tuning process. The context key should not be used for ungrounded skills."><%=example.getContext() %></textarea>
              </div> <!-- end div -->
              <div style="width:100%;"> <!-- start div "100%" -->
                <br /><br /><h2>Questions and Answers:</h2><br /><hr  style="width:97%;"/><br />
                <label for="question_<%=exampleCount%>">Question: </label><br /><input type="text" id="question_<%=exampleCount%>" name="question_<%=exampleCount%>" style="width: 90%;" title="A question for the model." value="<%=example.getQuestion() %>"><br />
                <label for="answer_<%=exampleCount%>">Answer: </label><br /><textarea id="answer_<%=exampleCount%>" name="answer_<%=exampleCount%>" rows="2" style="width: 90%;" title="The desired response from the model."><%=example.getAnswer() %></textarea><br />
              </div> <!-- end div "100%" -->
            </div> <!-- end div "border" -->
            <br /><br />
            <%
                exampleCount++;
              }
            %>
          </div> <!-- end div "mainpage" -->
        </form>
      </div><!-- End row-->
    </main>
    <footer>
    </footer>
  </body>
</html>