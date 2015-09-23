<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="i18n.UIStrings"/>

<div id="answerTemplate" class="answer" style="display: none;">
	<p><fmt:message key="tag.exam.creation.label.answer.text"/></p>
	<p>
		<input type="text" name="answer"/>
	</p>
	<p><fmt:message key="tag.exam.creation.label.answer.iscorrect"/></p>
	<p>
		<input type="checkbox" name="isCorrect"/>
	</p>
</div>