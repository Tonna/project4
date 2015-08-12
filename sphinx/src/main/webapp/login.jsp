<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<common:header title="Login to sphinx"/>

<div id="main">
    <div class="development message">Welcome to sphinx portal!</div>
    <form id="login" action="login" method="POST">
        <div>
            <p>Username: </p>
            <p>
                <input type="text" name="login"/>
            </p>
        </div>
        <div>
            <p>Password: </p>
            <p>
                <input type="password" name="password"/>
            </p>
        </div>
        <div>
            <p>
                <input type="Submit" value="Login"/>
            </p>
        </div>
    </form>
</div>

<common:footer/>