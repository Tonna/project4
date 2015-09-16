<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="answerTemplate" class="answer" style="display: none;">
	<p><c:out value="Text for answer"/></p>
	<p>
		<input type="text" name="answer"/>
	</p>
	<p><c:out value="is correct?"/></p>
	<p>
		<input type="checkbox" name="isCorrect"/>
	</p>
</div>