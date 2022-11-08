<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="mvc"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>

</head>
<body>
	<h1>&nbsp;Lobby ${lobby.id}</h1>
	<c:if test = "${lobby.game.name.contains('Oca')}">
	<a href="/lobbies/oca">Go Back To Lobby List</a><br><br>
	</c:if>
	<c:if test = "${lobby.game.name.contains('Parchis')}">
	<a href="/lobbies/parchis">Go Back To Lobby List</a><br><br>
	</c:if>
		<td><h3>&nbsp;&nbsp;<c:out value="CURRENT GAME: ${lobby.game}"/></h3></td>	 
			<table class="table table-striped">
				<tr>	
					<th>Host</th>	
					<th>Player 2</th>	
					<th>Player 3</th>	
					<th>Player 4</th>		
				</tr>
			<c:forEach items="${players}" var="player">
				<c:if test = "${lobby.host.login == player.login}">
					<td><b><c:out value="${player.login}"/></b></td>	 
				</c:if>
				<c:if test = "${lobby.host.login != player.login}">
					<td><c:out value="${player.login}"/></td>	 
				</c:if>
			</c:forEach>
		</table>
</body>
</html>