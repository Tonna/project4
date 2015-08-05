<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %><!DOCTYPE html>
<common:header title="Login to sphinx"/>
		<div id="allcontent">
			<div id="main">
				<div id="applicationName">Welcome to sphinx portal!</div>
				<form id="login" action="login" method="POST">
					<div class="tableRow">
						<p>Username: </p>

						<p>
							<input type="text" name="studentLogin"/>
						</p>
					</div>
					<div class="tableRow">
						<p>Password: </p>

						<p>
							<input type="password" name="studentPassword"/>
						</p>
					</div>
					<div class="tableRow">
						<p/>

						<p>
							<input type="Submit" value="Login"/>
						</p>
					</div>
				</form>
			</div>
		</div>
<common:footer/>