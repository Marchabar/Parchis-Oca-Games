<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="game" tagdir="/WEB-INF/tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"  href="/webjars/bootstrap/css/bootstrap.min.css" />
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>

<title>Parchis and Oca</title>
</head>
<body>
	<h1>&nbsp;Welcome to the Parchis and Oca games</h1>
	<h3>&nbsp;&nbsp;&nbsp;<a href="/users/register">Register</a></h3>
	<h3>&nbsp;&nbsp;&nbsp;<a href="/login">Login</a></h3>
	<h3>&nbsp;&nbsp;&nbsp;<a href="/rolldice">Roll the dice</a></h3>
	<div class="container">
		<br />
		<c:if test="${message != null}">
		<div class="alert alert-${messageType}">
			<c:out value="${message}"></c:out>
			<a href="#" class="close" data-dismiss="alert" aria-label="close">�</a>
		</div>
		</c:if>
	</div>
	<ul>	
		<sec:authorize access="hasAuthority('admin')">
    		<li><a href="/lobbies">All Lobbies (admin)</a></li>
		</sec:authorize>
		<li><a href="/lobbies/oca">Oca Lobbies</a></li>
		<li><a href="/lobbies/parchis">Parchis Lobbies</a></li>
		<sec:authorize access="hasAuthority('admin')">
			<li><a href="/users">Manage Users (admin)</a></li>
		</sec:authorize>
		<sec:authorize access="hasAuthority('admin')">
        <li><a href="/friends">All Friends (admin)</a></li>
		</sec:authorize>
		<li><a href="/friends/myfriends">Your friends</a></li>
		<li><a href="/playerstats">Personal Statistics</a></li>
		<li><a href="/playerstats/global">Global Statistics</a></li>

    </ul>
	<div class="row">
		<div class="col-md-12">
			<game:board ocaBoard="${ocaBoard}">
				<c:forEach items="${ocaBoard.pieces}" var="piece">
					<game:piece size="100" piece="${piece}"/>
				</c:forEach>
			</game:board>
		</div>
	</div>
</body>
</html>