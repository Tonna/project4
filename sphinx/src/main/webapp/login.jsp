<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<common:header title="Login to sphinx"/>

    <div id="main">
        <div id="applicationName">Welcome to sphinx portal!</div>
        <form id="login" action="login" method="POST">
            <div class="tableRow">
                <p>Username: </p>
                <p>
                    <input type="text" name="login"/>
                </p>
            </div>
            <div class="tableRow">
                <p>Password: </p>
                <p>
                    <input type="password" name="password"/>
                </p>
            </div>
            <div class="tableRow">
                <p>
                    <input type="Submit" value="Login"/>
                </p>
            </div>
        </form>
    </div>

<common:footer/>