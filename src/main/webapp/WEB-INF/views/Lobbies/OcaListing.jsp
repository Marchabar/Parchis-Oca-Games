<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"  href="/webjars/bootstrap/css/bootstrap.min.css" />
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>

<title>Oca Lobbies</title>
</head>
<body>
	<h2>Oca Lobbies:</h2>
	<div class="container">
		<br />
		<c:if test="${message != null}">
		<div class="alert alert-${messageType}">
			<c:out value="${message}"></c:out>
			<a href="#" class="close" data-dismiss="alert" aria-label="close">�</a>
		</div>
		</c:if>
	</div>
	<a href="/">Go Back To Main Page</a><br><br>
	<a href="/lobbies/createOca"><span class="glyphicon glyphicon-plus sucess" aria-hidden="true"></span>Create Lobby</a>
	<table class="table table-striped">
		<tr>
			<sec:authorize access="hasAuthority('admin')">
				<th>Matches</th>		
			</sec:authorize>	
			<th>LobbyID</th>
			<th>Game</th>
			<th>Host</th>
			<th>Players</th>
			<th>Enter</th>
			<sec:authorize access="hasAuthority('admin')">
				<th>Actions</th>
			</sec:authorize>
		</tr>
		<c:forEach items="${lobbiesOca}" var="lobby">
			<tr>
				<c:if test = "${!lobby.players.isEmpty()}">
				<sec:authorize access="hasAuthority('admin')">
					<td><a href="/lobbies/${lobby.id}/matches">See Matches</a></td>		
				</sec:authorize>		
				<td><c:out value="${lobby.id}"/></td>				
				<td><c:out value="${lobby.game}"/></td>
				<td><c:out value="${lobby.host.login}"/></td>
				<td><c:forEach items="${lobby.players}" var="player">
					<c:out value="- ${player.login}"/><br>
				</c:forEach>
				<c:out value="${fn:length(lobby.players)}/4"/></td>	
				<td><a href="/lobbies/${lobby.id}"><span class="glyphicon glyphicon-play-circle"></a> </td>		
				<sec:authorize access="hasAuthority('admin')">
					<td><a href="/lobbies/edit/${lobby.id}" ><span class="glyphicon glyphicon-pencil warning" aria-hidden="true"></span></a>
					&nbsp;<a href="/lobbies/delete/${lobby.id}"><span class="glyphicon glyphicon-trash alert" aria-hidden="true"></a></td>	
				</sec:authorize>
					</c:if>
					</tr>
		</c:forEach>
	</table>
</body>
</html>