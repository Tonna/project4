<%@ taglib prefix="examCreation" tagdir="/WEB-INF/tags/examCreation" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<common:header title="Exam creation"/>
<common:userSection/>

<examCreation:question/>
<examCreation:answer/>

<div id="main">
    <form action="exam?action=create" id="examCreationForm" onsubmit="return validateExam();" method="POST">
        <div id="errorMessage" class="validationError" style="display: none;">
            <p><c:out value="Unable to create exam - form filled out incorrectly</p>
        </div>

        <div>
            <p>Exam subject</p>
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
            <p>Exam name</p>
            <p>
                <input type="text" name="examName"/>
            </p>
        </div>
        <div id="questionsSection">
            <script type="text/javascript">addQuestion();</script>
        </div>
        <div id="addQuestionButton">
            <input type="button" value="Add question" onclick="addQuestion();return false;"/>
        </div>
        <div>
            <p>
                <input type="submit" value="Create exam"/>
            </p>
        </div>
    </form>
</div>

<common:footer/>