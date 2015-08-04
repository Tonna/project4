<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
    <common:header title="Exam picking"/>
    
    <div id="allcontent">
      <div id="header">
        <p>
          You entered as %USERNAME%
        </p>
        <p>
          <a href="user?action=logout">
            logout
          </a>
        </p>
  </div>
  
  <div id="main">
    <c:if test="${not empty takenExamName}">
        <p>
            You took "${takenExamName}" exam.
            You answered ${correctlyAnsweredQuestions} out of ${questionsInExam}
        </p>
    </c:if>
    <a href="exam?action=create">
      Create exam
    </a>
    
    <c:forEach var="subject" items="${examsBySubject}" varStatus="status">
      <div class="subject">
        <h2>
          ${subject.key}
        </h2>
        <c:forEach var="exam" items="${subject.value}" varStatus="status">
        <p>
          <a href="exam?action=take&id=${exam.id}"/>
            <c:out value="${exam.name}"/>
          </a>
        </p>
        </c:forEach>
      </div>
    </c:forEach>
    
  </div>
  </div>
  
  <common:footer/>