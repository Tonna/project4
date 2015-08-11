<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<common:header title="Exam taking"/>
<common:userSection/>

<div id="main">
    <form id="questions" class="development" action="exam?action=submit" method="POST">
        <h3 class="development">${exam.name}</h3>
        <input type="hidden" name="exam-id" value="${exam.id}"/>
        <c:forEach var="question" items="${exam.questions}">
            <div class="development question">
                <h4 class="development">${question.text}</h4>
                <c:forEach var="answer" items="${question.answers}">
                    <div class="development answer">
                        <p>${answer.text}</p>
                        <p>
                            <input type="checkbox" name="answer-id-for-question-id-${question.id}" value="${answer.id}"/>
                        </p>
                    </div>
                </c:forEach>
            </div>
        </c:forEach>
        <div class="development">
            <p>
                <input type="Submit" value="Submit"/>
            </p>
        </div>
    </form>
</div>

<common:footer/>