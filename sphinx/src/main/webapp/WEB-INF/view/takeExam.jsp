<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<common:header title="Exam taking"/>

<common:logout/>

    <div id="main">
        <form id="questions" action="exam?action=submit" method="POST">
            <h3>${exam.name}</h3>
            <input type="hidden" name="exam-id" value="${exam.id}"/>
            <c:forEach var="question" items="${exam.questions}">
                <div class="question">
                    <h3>${question.text}</h3>
                    <c:forEach var="answer" items="${question.answers}">
                        <div class="answer tableRow">
                            <p>${answer.text}</p>
                            <p>
                                <input type="checkbox" name="answer-id-for-question-id-${question.id}" value="${answer.id}"/>
                            </p>
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

<common:footer/>