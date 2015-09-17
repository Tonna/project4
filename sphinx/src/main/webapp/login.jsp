<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<common:header title="Login to sphinx"/>

<div id="main">
    <c:choose>
        <c:when test="${not empty authenticationFailed}">
            <div class="validationError">
                <p><c:out value="Login and password do not match existing user"/></p>
            </div>
        </c:when>
        <c:otherwise>
            <div class="development message"><c:out value="Welcome to sphinx portal! Please login."/></div>
        </c:otherwise>
    </c:choose>
    <form id="login" action="user?action=login" method="POST">
        <div>
            <p><c:out value="Username: "/></p>
            <p>
                <input type="text" name="login"/>
            </p>
        </div>
        <div>
            <p><c:out value="Password: "/></p>
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