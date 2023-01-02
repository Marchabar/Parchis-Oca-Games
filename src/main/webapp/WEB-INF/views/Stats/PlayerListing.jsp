<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ocaParchis" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"  href="/webjars/bootstrap/css/bootstrap.min.css" />
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>

<ocaParchis:layout pageName="home">
	<title>All stats per game for <c:out value="${user.login}"></c:out></title>
</head>
<body style="background-color:#ececec">
	<h2 style="font-family:monospace">All stats per game for <c:out value="${user.login}"></c:out>:</h2>
	<div class="container">
		<br />
		<c:if test="${message != null}">
		<div class="alert alert-${messageType}">
			<c:out value="${message}"></c:out>
			<a href="#" class="close" data-dismiss="alert" aria-label="close"> </a>
		</div>
		</c:if>
	</div>
	<a class="btn btn-danger" href="/playerstats"><span class="glyphicon glyphicon-zoom-out" aria-hidden="true"></span> See overall statistics</a>
<<<<<<< HEAD
=======
	<c:if test="${currentLobby != null}">
	<div style="float:right">
		<a class="btn btn-danger" href="/lobbies/${currentLobby.id}">Go back to lobby</a><br><br>
	</div>
</c:if>
>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e
	<table class="table table-striped">
		<tr>		
			<th>Match ID</th>	
            <th>Dice Rolls</th>
			<th>Color chosen</th>
			<th>Ending position</th>
            <th>Gooses stepped</th>
			<th>Wells fallen into</th>
            <th>Times lost in labyrinths</th>
            <th>Prisons entered</th>
			<th>Deaths</th>
		</tr>
		<c:forEach items="${stats}" var="stat" varStatus="status">
			<tr>
				<c:if test="${matches[status.index].winner != null}">
					<td><a href = "/matches/${matches[status.index].id}">${matches[status.index].id}</a></td>
					<td><c:out value="${stat.numDiceRolls}"/></td>
					<td><c:out value="${stat.playerColor}"/></td>
					<td><c:out value="${stat.position}"/></td>
					<td><c:out value="${stat.numberOfGooses}"/></td>
					<td><c:out value="${stat.numberOfPlayerWells}"/></td>
					<td><c:out value="${stat.numberOfLabyrinths}"/></td>
					<td><c:out value="${stat.numberOfPlayerPrisons}"/></td>
					<td><c:out value="${stat.numberOfPlayerDeaths}"/></td>
				</c:if>
			</tr>
		</c:forEach>
	</table>
</body>
</ocaParchis:layout>

</html>