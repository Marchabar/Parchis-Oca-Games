<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="mvc"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ocaParchis" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>

<ocaParchis:layout pageName="home">
</head>
<body style="background-color:#ececec">
	<h1 style="font-family:monospace">Lobby hosted by ${lobby.host.login}:</h1>
	<c:if test = "${matchTakingPlace}">
		<h1 style="font-family:monospace">Currently a match is taking place! Please wait while your colleagues end the match.</h1>
	</c:if>
	<c:if test = "${lobby.game.name.contains('Oca')}">
	<a class="btn btn-danger" href="/lobbies/oca">Go Back To Lobby List</a><br><br>
	</c:if>
	<c:if test = "${lobby.game.name.contains('Parchis')}"> 
	<a class="btn btn-danger" href="/lobbies/parchis">Go Back To Lobby List</a><br><br>
	</c:if>
	<c:if test="${lobby.host.equals(loggedUser)}">
	<a class="btn btn-danger" href="/lobbies/edit/${lobby.id}"style="color:white"><span class="glyphicon glyphicon-pencil warning" aria-hidden="true"></span> Edit Lobby</a>
</c:if>
		<td><h3 style="font-family:monospace">&nbsp;&nbsp;<c:out value="CURRENT GAME: ${lobby.game}"/></h3></td>
			<table class="table table-striped">
				<tr>	
					<th>Host</th>
					<c:if test = "${players.size()>=2}">
					<th>Player 2</th>
					</c:if>
					<c:if test = "${players.size()>=3}">
					<th>Player 3</th>
					</c:if>
					<c:if test = "${players.size()==4}">
					<th>Player 4</th>	
					</c:if>	
				</tr>
				<tr>
			<c:forEach items="${players}" var="player">
				<c:if test = "${lobby.host.login == player.login}">
					<td style="color:${player.prefColor.rgb}"><b><c:out value="${player.login}"/></b></td>	 
				</c:if>
				<c:if test = "${lobby.host.login != player.login}">
					<td style="color:${player.prefColor.rgb}"><c:out value="${player.login}"/></td>	 
				</c:if>
			</c:forEach>
		</tr>
		<tr>
			<c:if test = "${lobby.host.login == loggedUser.login && players.size()!=1}">
				<td><c:out value="Kick?"></c:out></td>
				<c:forEach items="${players}" var="player">
					<c:if test = "${lobby.host.login != player.login}">
						<td><a href="/lobbies/${lobby.id}/kick/${player.id}" style="color:#d9534f"style="color:#d9534f"><span style="font-size: 15px" class="glyphicon glyphicon-remove-circle" aria-hidden="true"></a></td>
					</c:if>
				</c:forEach>
			</c:if>
		</tr>
		</table>
		<c:if test = "${lobby.host.login == loggedUser.login}">
			<c:if test = "${fn:length(lobby.players) > 1}">
				<a class ="btn btn-danger" href="/lobbies/${lobby.id}/createMatch"><span class="glyphicon glyphicon-play" aria-hidden="true"></span> Start game!</a>
			</c:if>
		</c:if>
		<td><h3 style="font-family:monospace" align="middle">&nbsp;&nbsp;<c:out value="${now}"/></h3></td>
		<div style="font-family:monospace" align="middle">
			<a class="btn btn-danger" style="background-color:#f43e3e" href="/lobbies/${lobby.id}/RED">Choose color red</a>
			<a class="btn btn-danger" style="background-color:#3d5cf9" href="/lobbies/${lobby.id}/BLUE">Choose color blue</a>
			<a class="btn btn-danger" style="background-color:#4c9c24" href="/lobbies/${lobby.id}/GREEN">Choose color green</a>
			<a class="btn btn-danger" style="background-color:#bf870f" href="/lobbies/${lobby.id}/YELLOW">Choose color yellow</a>
		</div>



</body>
</ocaParchis:layout>

</html>