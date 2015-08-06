<%@ taglib prefix="examCreation" tagdir="/WEB-INF/tags/examCreation" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>

<common:header title="Exam creation"/>
<examCreation:question />
<examCreation:answer />

<div id="allcontent">
    <div id="header">
        <div id="applicationName">
            sphinx
        </div>
        <div id="userData">
            <p>Welcome %USERNAME%</p>
            <a href="user?action=logout">logout</a>
        </div>
    </div>

    <div id="main">
        <form action="exam?action=view" id="questions" method="POST">
            <input type="button" id="addQuestionButton" value="Add question" onclick="addQuestion();return false;">
            <div class="tableRow"><p><input type="submit" value="Create exam"></p></div>
        </form>
    </div>
</div>

<common:footer/>