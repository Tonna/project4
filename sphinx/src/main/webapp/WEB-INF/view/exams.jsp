<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://yakovchuk.com/jsp/jstl/collection" prefix="col" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<common:header title="page.exam.picking.title"/>
<common:userSection/>

<div id="main">

    <c:if test="${not empty takenExamName}">
        <div id="examResult" class="goodMessage">
            <p>
                <fmt:message key="page.exam.picking.message.submit.pre.exam.name"/> <c:out value="'${takenExamName}'."/>
            </p>
            <p>
                <c:out value="${correctlyAnsweredQuestions}"/> <fmt:message key="page.exam.picking.message.submit.outOf"/> <c:out value="${questionsInExam}"/> <fmt:message key="page.exam.picking.message.submit.questions.answered"/>
            </p>
        </div>
    </c:if>

    <c:if test="${not empty createdExamName}">
        <div class="goodMessage">
            <p>
                <fmt:message key="page.exam.picking.message.creation.success"/> <c:out value="'${createdExamName}' "/>
            </p>
        </div>
    </c:if>

    <c:if test="${col:contains(applicationScope['examCreationRoles'], sessionScope.profile.roles)}">
        <div>
            <a href="exam?action=creationForm"><fmt:message key="page.exam.picking.link.create.exam"/></a>
        </div>
    </c:if>

    <c:if test="${col:contains(applicationScope['examTakingRoles'], sessionScope.profile.roles)}">
        <div class="development subjectList">
                <c:forEach var="subject" items="${examsBySubject}" varStatus="status">
                    <div class="development subject">
                        <h2><c:out value="${subject.key}"/></h2>
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