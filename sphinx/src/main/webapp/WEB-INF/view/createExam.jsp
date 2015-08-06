<%@ taglib prefix="examCreation" tagdir="/WEB-INF/tags/examCreation" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>

<common:header title="Exam creation"/>
<examCreation:question/>
<examCreation:answer/>
<common:logout/>

    <div id="main">
        <form action="exam?action=view" id="questions" method="POST">
            <input type="button" id="addQuestionButton" value="Add question" onclick="addQuestion();return false;">
            <div class="tableRow">
                <p>
                    <input type="submit" value="Create exam">
                </p>
            </div>
        </form>
    </div>

<common:footer/>