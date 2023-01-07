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
	<title>Overall global statistics</title>
</head>
<body style="background-color:#ececec">
	<h2 style="font-family:monospace">Overall global statistics:</h2>
	<div class="container">
		<br/>
		<c:if test="${message != null}">
		<div class="alert alert-${messageType}">
			<c:out value="${message}"></c:out>
			<a href="#" class="close" data-dismiss="alert" aria-label="close"> </a>
		</div>
		</c:if>
	</div>
	<a class="btn btn-danger" href="/playerstats/global/history"><span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span> See full history</a>
	<c:if test="${currentLobby != null}">
	<div style="float:right">
		<a class="btn btn-danger" href="/lobbies/${currentLobby.id}">Go back to lobby</a><br><br>
	</div>
</c:if>
<h3 style="font-family:monospace">General:</h3>
<table class="table table-striped">
	<tr>			
		<th>Total Dice Rolls</th>
		<th>Most chosen color</th>
	</tr>
		<tr>
			<td><c:out value="${stat.numDiceRolls}"/></td>
			<td><c:out value="${stat.playerColor}"/></td>
		</tr>
</table>
<h3 style="font-family:monospace">Oca:</h3>
<table class="table table-striped">
	<tr>			
		<th>Tiles Advanced</th>
		<th>Gooses stepped</th>
		<th>Wells fallen into</th>
		<th>Times lost in labyrinths</th>
		<th>Prisons entered</th>
		<th>Deaths</th>
		<th>Inns stayed</th>
	</tr>
		<tr>
			<td><c:out value="${stat.position}"/></td>
			<td><c:out value="${stat.numberOfGooses}"/></td>
			<td><c:out value="${stat.numberOfPlayerWells}"/></td>
			<td><c:out value="${stat.numberOfLabyrinths}"/></td>
			<td><c:out value="${stat.numberOfPlayerPrisons}"/></td>
			<td><c:out value="${stat.numberOfPlayerDeaths}"/></td>
			<td><c:out value="${stat.numberOfInns}"/></td>
		</tr>
</table>
<h3 style="font-family:monospace">Parchis:</h3>
<table class="table table-striped">
	<tr>			
		<th>Chips Taken Out</th>
		<th>Finished Chips</th>
		<th>Chips Eaten</th>
		<th>Barriers Formed</th>
		<th>Barriers Rebounds</th>
		<th>Cheats</th>
	</tr>
		<tr>
			<td><c:out value="${stat.numberOfChipsOut}"/></td>
			<td><c:out value="${stat.numberOfEndChips}"/></td>
			<td><c:out value="${stat.numberOfChipsEaten}"/></td>
			<td><c:out value="${stat.numberOfBarriersFormed}"/></td>
			<td><c:out value="${stat.numberOfBarrierRebound}"/></td>
			<td><c:out value="${stat.numberOfCheats}"/></td>
		</tr>
</table>

</body>
</ocaParchis:layout>

</html>