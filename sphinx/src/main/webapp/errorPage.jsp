<%@ taglib prefix="common" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<common:header title="Error"/>

<c:out value="Something wrong happened"/>

<a href="/sphinx">
    <c:out value="Get out of here"/>
</a>

<common:footer/>