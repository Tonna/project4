<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<common:header title="Exam picking"/>
<common:userSection/>

<div id="main" class="development">

    <c:if test="${not empty takenExamName}">
        <div id="examResult" class="development">
            <p>
                You took "${takenExamName}" exam.
            </p>
            <p>
                You answered ${correctlyAnsweredQuestions} out of ${questionsInExam}
            </p>
        </div>
    </c:if>

    <!-- TODO if role tutor then display-->
    <div class="development">
        <a href="exam?action=create">Create exam</a>
    </div>

    <div class="development subjectList">
        <c:forEach var="subject" items="${examsBySubject}" varStatus="status">
            <div class="development subject">
                <h2>${subject.key}</h2>
                <c:forEach var="exam" items="${subject.value}" varStatus="status">
                    <p>
                        <a href="exam?action=take&id=${exam.id}"/>
                            <c:out value="${exam.name}"/>
                        <a>
                    </p>
                </c:forEach>
            </div>
        </c:forEach>
    </div>

</div>

<common:footer/>