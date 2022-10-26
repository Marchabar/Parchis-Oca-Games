<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"  href="/webjars/bootstrap/css/bootstrap.min.css" />
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>

<title>Andalusian Parliament</title>
</head>
<body>
	<h1>Welcome to the Andalusian Parliament</h1>
	<div class="container">
		<br />
		<c:if test="${mensaje != null}">
		<div class="alert alert-${tipomensaje}">
			<c:out value="${mensaje}"></c:out>
			<a href="#" class="close" data-dismiss="alert" aria-label="close">X</a>
		</div>
		</c:if>
	</div>
	<ul>
        <li><a href="/boards">Manage Boards</a></li>
        <li><a href="/members">Manage Members</a></li>
        <li><a href="/rooms">Manage Rooms</a></li>
        <li><a href="">Manage Sessions</a></li>
    </ul>
</body>
</html>