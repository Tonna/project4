<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<common:header title="Login to sphinx"/>

<div id="main" class="development">
    <div class="development message">Welcome to sphinx portal!</div>
    <form id="login" class="development" action="login" method="POST">
        <div class="development">
            <p>Username: </p>
            <p>
                <input type="text" name="login"/>
            </p>
        </div>
        <div class="development">
            <p>Password: </p>
            <p>
                <input type="password" name="password"/>
            </p>
        </div>
        <div class="development">
            <p>
                <input type="Submit" value="Login"/>
            </p>
        </div>
    </form>
</div>

<common:footer/>