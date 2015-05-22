<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<common:header title="Exam taking"/>

<div id="allcontent">
    <div id="header">
        <p>You entered as %USERNAME%</p>

        <p>
            <a href="user?action=logout">logout</a>
        </p>
    </div>
    <div id="main">
        <form id="questions" action="exam?action=view" method="POST">
			<h3>${exam.name}</h3>
            <input type="hidden" name="exam-id" value="${exam.id}"/>
			<c:forEach var="question" items="${exam.questions}">
				<div class="question">
					<h3>${question.text}</h3>
					<input type="hidden" name="question-id" value="${question.id}"/>
						<c:forEach var="answer" items="${question.answers}">
							<div class="answer tableRow">
								<p>${answer.text}</p>
								<p><input type="checkbox" name="answer-id" value="${answer.id}"/></p>
							</div>
						</c:forEach>
				</div>
			</c:forEach>
            <div class="tableRow">
                <p>
                    <input class="bigButton" type="Submit" value="Submit"/>
                </p>
            </div>
        </form>
    </div>
</div>
<common:footer/>