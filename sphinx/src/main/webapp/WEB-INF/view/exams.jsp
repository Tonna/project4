<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://yakovchuk.com/jsp/jstl/collection" prefix="col" %>

<common:header title="Exam picking"/>
<common:userSection/>

<div id="main">

    <c:if test="${not empty takenExamName}">
        <div id="examResult">
            <p>
                You took "${takenExamName}" exam.
            </p>
            <p>
                You answered ${correctlyAnsweredQuestions} out of ${questionsInExam}
            </p>
        </div>
    </c:if>

    <c:if test="${col:contains(applicationScope['examCreationRoles'], sessionScope.user.roles)}">
        <div>
            <a href="exam?action=creationForm">Create exam</a>
        </div>
    </c:if>

    <c:if test="${col:contains(applicationScope['examTakingRoles'], sessionScope.user.roles)}">
        <div class="development subjectList">
                <c:forEach var="subject" items="${examsBySubject}" varStatus="status">
                    <div class="development subject">
                        <h2>${subject.key}</h2>
                        <c:forEach var="exam" items="${subject.value}" varStatus="status">
                            <p>
                                <a href="exam?action=take&id=${exam.id}">
                                    <c:out value="${exam.name}"/>
                                </a>
                            </p>
                        </c:forEach>
                    </div>
                </c:forEach>
            </div>
    </c:if>


</div>

<common:footer/>