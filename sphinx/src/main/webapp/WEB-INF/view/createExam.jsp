<%@ taglib prefix="examCreation" tagdir="/WEB-INF/tags/examCreation" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>

<common:header title="Exam creation"/>
<common:userSection/>

<examCreation:question/>
<examCreation:answer/>

<div id="main" class="development">
    <form action="exam?action=view" id="questions" class="development" method="POST">
        <div id="addQuestionButton" class="development">
            <input type="button" value="Add question" onclick="addQuestion();return false;"/>
        </div>
        <div class="development">
            <p>
                <input type="submit" value="Create exam"/>
            </p>
        </div>
    </form>
</div>

<common:footer/>