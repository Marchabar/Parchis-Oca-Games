<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ocaParchis" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"  href="/webjars/bootstrap/css/bootstrap.min.css" />
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>

<ocaParchis:layout pageName="home">
	<title>Parchis and Oca</title>
	</head>
	<body>
		<h1 align="middle">&nbsp;Welcome to the Parchis and Oca games</h1>
		<h3 align="middle">&nbsp;&nbsp;&nbsp;<a href="/rolldice">Roll the dice</a></h3>
		<div class="container">
			<br />
			<c:if test="${message != null}">
			<div class="alert alert-${messageType}">
				<c:out value="${message}"></c:out>
				<a href="#" class="close" data-dismiss="alert" aria-label="close">�</a>
			</div>
			</c:if>
		</div>
		
		<div align="middle">
			<spring:url value="/resources/images/oca.jpg" htmlEscape="true" var="ocaImage"/>
			<img src="${ocaImage}" width="400" height="400"/>
					
			<spring:url value="/resources/images/parchis.png" var="parchisImage"/>
			<img src="${parchisImage}" width="400" height="400"/>
		</div>
				
	</body>
</ocaParchis:layout>
</html>