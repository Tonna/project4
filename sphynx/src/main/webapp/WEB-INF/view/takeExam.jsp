<!DOCTYPE html>
<html>
	<head>
		<link type="text/css" rel="stylesheet" href="stylesheets/sphynx.css"/>
		<meta charset="utf-8"/>
		<title>Exam passage</title>
	</head>
	<body>
		<div id="allcontent">
			<div id="header">
				<p>You entered as %USERNAME%</p>

				<p>
                    <a href="user?action=logout">logout</a>
				</p>
			</div>
			<div id="main">
				<form id="questions" action="exam?action=view" method="POST">
					<input type="hidden" name="test-name" value="test1"/>

					<div class="question">
						<input type="hidden" name="question-name" valeu="question1"/>

						<h3>Question text
                    LONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONGLONG</h3>

						<div class="answer tableRow">
							<p>A</p>

							<p>
								<input type="radio" name="answer1" value="A"/>
							</p>
						</div>
						<div class="answer tableRow">
							<p>B</p>

							<p>
								<input type="radio" name="answer1" value="B"/>
							</p>
						</div>
					</div>
					<div class="question">
						<input type="hidden" name="question-name" value="question2"/>

						<h3>Question 2 text</h3>

						<div class="answer tableRow">
							<p>A</p>

							<p>
								<input type="radio" name="answer2" value="A"/>
							</p>
						</div>
						<div class="answer tableRow">
							<p>B</p>

							<p>
								<input type="radio" name="answer2" value="B"/>
							</p>
						</div>
					</div>
					<div class="tableRow">
						<p>
							<input class="bigButton" type="Submit" value="Submit"/>
						</p>
					</div>
				</form>
			</div>
		</div>
	</body>
</html>