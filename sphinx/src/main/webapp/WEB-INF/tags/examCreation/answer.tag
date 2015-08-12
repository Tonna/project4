<%@ attribute name="isHidden" required="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
    <c:when test="${isHidden == false}">
        <div class="answer">
    </c:when>
    <c:otherwise>
        <div id="answerTemplate" class="answer" style="display: none;">
    </c:otherwise>
</c:choose>

	<p>Text for answer</p>
	<p>
		<input type="text" name="answer"/>
	</p>
	<p>is correct?</p>
	<p>
		<input type="checkbox" name="isCorrect"/>
	</p>
</div>