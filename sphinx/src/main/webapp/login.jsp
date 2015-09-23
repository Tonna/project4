<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="i18n.UIStrings"/>
<common:header title="page.login.title"/>


<div id="main">
    <c:choose>
        <c:when test="${not empty authenticationFailed}">
            <div class="validationError">
                <p><fmt:message key="page.login.message.login.failure"/></p>
            </div>
        </c:when>
        <c:otherwise>
            <div class="development message"><fmt:message key="page.login.message.welcome"/></div>
        </c:otherwise>
    </c:choose>
    <form id="login" action="user?action=login" method="POST">
        <div>
            <p><fmt:message key="page.login.label.login"/></p>
            <p>
                <input type="text" name="login"/>
            </p>
        </div>
        <div>
            <p><fmt:message key="page.login.label.password"/></p>
            <p>
                <input type="password" name="password"/>
            </p>
        </div>
        <div>
            <p>
                <input type="Submit" value="<fmt:message key="page.login.button.login"/>"/>
            </p>
        </div>
    </form>
</div>

<common:footer/>