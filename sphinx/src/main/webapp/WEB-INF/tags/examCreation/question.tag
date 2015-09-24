<%@ taglib prefix="examCreation" tagdir="/WEB-INF/tags/examCreation" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${profile.languageCode}" />

<div id="questionTemplate" class="question" style="display: none;">
    <p><fmt:message key="page.exam.creation.label.question.text"/></p>
    <p>
        <textarea rows="2" name="question"></textarea>
    </p>
    <div class="answersSection">
    </div>
    <div class="addAnswerButton">
        <input type="button" value="<fmt:message key="page.exam.creation.button.add.answer"/>" onclick="addAnswer(this.parentNode.parentNode);return false;"/>
    </div>
</div>
