<%@ taglib prefix="examCreation" tagdir="/WEB-INF/tags/examCreation" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>

<common:header title="Exam creation"/>
<common:userSection/>

<examCreation:question/>
<examCreation:answer/>

<div id="main">
    <form action="exam?action=create" id="examCreationForm" method="POST">
        <div>
            <p>Exam subject</p>
            <p>
                <input type="text" name="subject"/>
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