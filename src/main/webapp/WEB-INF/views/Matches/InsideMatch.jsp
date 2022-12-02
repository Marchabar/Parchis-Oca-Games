<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ocaParchis" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"  href="/webjars/bootstrap/css/bootstrap.min.css" />
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>

<ocaParchis:layout pageName="home">
	<title>Match ${match.id}</title>
	</head>
	<body style="background-color:#ececec">
		<h2 style="font-family:monospace">Match ${match.id}</h2>
		<h2 style="font-family:monospace">${match.playerToPlay.user.login}'s turn</h2>
		<c:forEach items="${match.playerStuck}" var="stuckplayer">
			<c:out value="${stuckplayer.user.login}'s turn was skipped..."></c:out>
		</c:forEach>
		<c:if test="${match.lastRoll!=0}">
			<h2 style="font-family:monospace">${prevPlayer.user.login} rolled a <span style="color:#d9534f">${match.lastRoll}</span>!!</h2>
			<div style="text-align: center;">
				<spring:url value="/resources/images/dice${match.lastRoll}.PNG" htmlEscape="true" var="diceNumber"/>
				<img src="${diceNumber}" style ="margin: 30px;"/>
			</div>
		</c:if>
		<div class="container">
			<br />
			<c:if test="${message != null||param.message != null}">
			<div class="alert alert-${messageType}">
				<c:out value="${message}"></c:out>
				<c:out value="${param.message}"></c:out>
				<a href="#" class="close" data-dismiss="alert" aria-label="close">�</a>
			</div>
			</c:if>
		</div>
		<table class="table table-striped">
			<tr>			
				<th>Id</th>
				<th>Username</th>
				<th>Position</th>
				<th>Dice Rolls</th>
				<th>Turns stuck</th>
				<th>Tile</th>
			</tr>
			<c:forEach items="${match.playerStats}" var="playerstats">
				<tr>				
					<td><c:out value="${playerstats.id}"/></td>
					<td><c:out value="${playerstats.user.login}"/></td>
					<td><c:out value="${playerstats.position}"/></td>
					<td><c:out value="${playerstats.numDiceRolls}"/></td>
					<td><c:out value="${playerstats.turnsStuck}"/></td>
					<td><c:out value="${allTiles[playerstats.position-1].type.name}"/></td>
				</tr>
			</c:forEach>
			<c:if test="${match.playerToPlay.user==loggedUser}">
			<a class="btn btn-danger" href="/matches/${match.id}/advance" > Throw Dice!</a><br><br>
		</c:if>
		</table>
	</body>
	</ocaParchis:layout>
</html>


