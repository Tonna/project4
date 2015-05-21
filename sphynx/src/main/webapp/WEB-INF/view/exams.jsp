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
    <a href="exam?action=create">
      Create exam
    </a>
    
    <c:forEach var="exam" items="${exams}" varStatus="status">
      <div class="subject">
        <h2>
          ${exam.subject}
        </h2>
        <p>
          <a href="exam?action=take&id=${exam.id}"/>
            <c:out value="${exam.name}"/>
          </a>
        </p>
      </div>
    </c:forEach>
    
  </div>
  </div>
  
  <common:footer/>