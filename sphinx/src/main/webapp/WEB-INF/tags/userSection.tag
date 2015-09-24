<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div id="userSection">
    <p><fmt:message key="page.home.pre.login"/> ${sessionScope.profile.login}.
    <a href="user?action=logout"><fmt:message key="page.home.link.logout"/></a></p>
</div>