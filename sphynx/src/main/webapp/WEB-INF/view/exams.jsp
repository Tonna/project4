<!DOCTYPE html>
<html>
	<head>
		<link type="text/css" rel="stylesheet" href="stylesheets/sphynx.css"/>
		<meta charset="utf-8"/>
		<title>Test picking</title>
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
				<a href="exam?action=create">Create exam</a>
				<div class="subject">
					<h2>Subject something</h2>

					<p>
						<a href="exam?action=take&id=1">Test1</a>
					</p>

					<p>
						<a href="exam?action=take&id=2">Test2</a>
					</p>
				</div>
				<div class="subject">
					<h2>Subject other</h2>

					<p>
						<a href="exam?action=take&id=3">Test1</a>
					</p>

					<p>
						<a href="exam?action=take&id=4">Test2</a>
					</p>
				</div>
			</div>
		</div>
	</body>
</html>