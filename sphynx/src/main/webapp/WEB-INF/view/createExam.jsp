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
        <examCreation:question />
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