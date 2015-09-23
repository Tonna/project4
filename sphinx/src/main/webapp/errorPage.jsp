<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="i18n.UIStrings"/>

<common:header title="Error"/>

<fmt:message key="page.error.message.error"/>

<a href="/sphinx">
    <fmt:message key="page.error.link.home"/>
</a>

<common:footer/>