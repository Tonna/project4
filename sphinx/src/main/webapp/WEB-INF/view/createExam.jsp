<%@ taglib prefix="examCreation" tagdir="/WEB-INF/tags/examCreation" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${profile.languageCode}" />
<common:header title="page.exam.creation.title"/>
<common:userSection/>

<examCreation:question/>
<examCreation:answer/>

<div id="main">
    <form action="exam?action=create" id="examCreationForm" onsubmit="return validateExam();"
    method="POST" accept-charset="UTF-8">
        <div id="errorMessage" class="validationError" style="display: none;">
            <p><fmt:message key="page.exam.creation.error.validation"/></p>
        </div>

        <div>
            <p><fmt:message key="page.exam.creation.label.exam.subject"/></p>
            <p>
                <input type="text" name="subject" list="subjects-list"/>
                <datalist id="subjects-list">
                <!--[if !IE]><!-->
                <select>
                <!--<![endif]-->
                    <c:forEach var="subject" items="${subjects}" varStatus="status">
                        <option value="${subject.name}"></option>
                    </c:forEach>
                <!--[if !IE]><!-->
                </select>
                <!--<![endif]-->
                </datalist>
            </p>
        </div>
        <div>
            <p><fmt:message key="page.exam.creation.label.exam.name"/></p>
            <p>
                <input type="text" name="examName"/>
            </p>
        </div>
        <div id="questionsSection">
            <script type="text/javascript">addQuestion();</script>
        </div>
        <div id="addQuestionButton">
            <input type="button" value="<fmt:message key="page.exam.creation.button.add.question"/>" onclick="addQuestion();return false;"/>
        </div>
        <div>
            <p>
                <input type="submit" value="<fmt:message key="page.exam.creation.button.create.exam"/>"/>
            </p>
        </div>
    </form>
</div>

<common:footer/>