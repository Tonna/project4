<!DOCTYPE html>
<html>
	<head>
		<link type="text/css" rel="stylesheet" href="stylesheets/sphynx.css">
		<meta charset="utf-8">
		<title>Exam creation</title>
		<script type="text/javascript">
			function addQuestion(){
				var addQuestionHtml = document.getElementById('questionFormHTML').value;
				var new_question=document.createElement('div');
				new_question.className = "question";
				new_question.innerHTML=addQuestionHtml;
				document.getElementById('questions').appendChild(new_question);
				document.getElementById('questions').appendChild(document.getElementById('addQuestionButton'));
			}
		</script>
		<script type="text/javascript">
			function addAnswer(question, button){
				var addAnswerHtml = document.getElementById('answerFormHTML').value;
				var answer=document.createElement('div');
				answer.className="answer tableRow";
				answer.innerHTML=addAnswerHtml;
				question.appendChild(answer);
				question.appendChild(button);
			}
		</script>
	</head>
	<body onload="addQuestion()">
		<!--Escaped HTML of question form is here-->
		<input type="hidden" id="questionFormHTML" value="
			&lt;div class=&quot;tableRow&quot;&gt;
				&lt;p&gt;Text for question&lt;/p&gt;
				&lt;p&gt;&lt;textarea rows=&quot;2&quot; name=&quot;question&quot;&gt;&lt;/textarea&gt;&lt;/p&gt;
			&lt;/div&gt;
			&lt;input type=&quot;button&quot; id=&quot;addAnswerButton&quot; value=&quot;Add answer&quot; onclick=&quot;addAnswer(this.parentNode, this);return false;&quot;&gt;
		">
		<!--Escaped HTML of answer form is here-->
		<input type="hidden" id="answerFormHTML" value="
			&lt;p&gt;Text for answer&lt;/p&gt;
			&lt;p&gt;&lt;input type=&quot;text&quot; name=&quot;answer&quot;&gt;&lt;/p&gt;
			&lt;p&gt;is correct?&lt;/p&gt;
			&lt;p&gt;&lt;input type=&quot;checkbox&quot; name=&quot;is_right&quot;&gt;&lt;/p&gt;
		">
		<div id="allcontent">
			<div id="header">
				<div id="applicationName">
					Sphynx
				</div>
				<div id="userData">				
					<p>Welcome %USERNAME%</p>
                    <a href="user?action=logout">logout</a>
				</div>
			</div>

			<div id="main">
				<form action="exam?action=view" id="questions" method="POST">
					<input type="button" id="addQuestionButton" value="Add question" onclick="addQuestion();return false;">
					<div class="tableRow"><p><input type="submit" value="Create exam"></p></div>
				</form>
			</div>
		</div>
	</body>
</html>