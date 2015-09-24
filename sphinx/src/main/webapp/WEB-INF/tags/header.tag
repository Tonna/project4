<%@ attribute name="title" required="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<link type="text/css" rel="stylesheet" href="stylesheets/sphinx.css"/>
		<meta charset="utf-8"/>
		<title><fmt:message key="${title}"/></title>
		<script src="js/dynamicExamCreation.js"></script>
		<script src="js/vendor/jquery-2.1.4.js"></script>
	</head>
	<body>
         <div id="allcontent">
            <h1 id="applicationName"><fmt:message key="header.application.name"/></h1>
