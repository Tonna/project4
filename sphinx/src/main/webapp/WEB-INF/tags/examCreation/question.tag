<%@ taglib prefix="examCreation" tagdir="/WEB-INF/tags/examCreation" %>

<div id="questionTemplate" class="question" style="display: none;">
    <p><c:out valie="Text for question"/></p>
    <p>
        <textarea rows="2" name="question"></textarea>
    </p>
    <div class="answersSection">
    </div>
    <div class="addAnswerButton">
        <input type="button" value="Add answer" onclick="addAnswer(this.parentNode.parentNode);return false;"/>
    </div>
</div>

