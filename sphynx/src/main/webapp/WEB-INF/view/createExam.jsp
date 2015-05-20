<%@ taglib prefix="examCreation" tagdir="/WEB-INF/tags/examCreation" %>
<!DOCTYPE html>
<html>
	<head>
		<link type="text/css" rel="stylesheet" href="stylesheets/sphynx.css">
		<meta charset="utf-8">
		<title>Exam creation</title>
		<script src="js/dynamicExamCreation.js"></script>
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
        <examCreation:answer />
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