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
	<div style="text-align: center">
		<h2 style="color:#c58300; font-family:monospace"><span class="glyphicon glyphicon-star-empty" aria-hidden="true"></span>${match.winner.user.login} won!<span class="glyphicon glyphicon-star-empty" aria-hidden="true"></span></h2>
		<img style="border: 3px solid #c58300" src="https://media.tenor.com/f5NH7emDMrgAAAAC/lord-of-the-rings-clapping.gif">
	</div>
	<p><h2 style="font-family:monospace">General match stats</h2></p>
	<table class="table table-striped">
		<tr>			
			<th>Username</th>
			<th>Position</th>
			<th>Turn</th>
			<th>Gooses</th>
            <th>Well</th>
            <th>Labyrinths</th>
            <th>Prison</th>
            <th>Death</th>
		</tr>
		<c:forEach items="${match.playerStats}" var="playerstats">
			<tr>				
				<td><c:out value="${playerstats.user.login}"/></td>
				<td><c:out value="${playerstats.position}"/></td>
				<td><c:out value="${playerstats.numDiceRolls}"/></td>
				<td><c:out value="${playerstats.numberOfGooses}"/></td>
                <td><c:out value="${playerstats.numberOfPlayerWells}"/></td>
                <td><c:out value="${playerstats.numberOfLabyrinths}"/></td>
                <td><c:out value="${playerstats.numberOfPlayerPrisons}"/></td>
                <td><c:out value="${playerstats.numberOfPlayerDeaths}"/></td>
			</tr>
		</c:forEach>
	</table>
</body>
</ocaParchis:layout>
</html>