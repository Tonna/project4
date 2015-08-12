<%@ attribute name="isHidden" required="false"%>
<%@ taglib prefix="examCreation" tagdir="/WEB-INF/tags/examCreation" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
    <c:when test="${isHidden == false}">
        <div class="question">
    </c:when>
    <c:otherwise>
        <div id="questionTemplate" class="question" style="display: none;">
    </c:otherwise>
</c:choose>

    <p>Text for question</p>
    <p>
        <textarea rows="2" name="question"></textarea>
    </p>
    <div class="answersSection">
        <examCreation:answer isHidden="false"/>
        <examCreation:answer isHidden="false"/>
    </div>
    <div class="addAnswerButton">
        <input type="button" value="Add answer" onclick="addAnswer(this.parentNode.parentNode);return false;"/>
    </div>
</div>

