<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
			<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
				<%@ taglib prefix="ocaParchis" tagdir="/WEB-INF/tags" %>

					<!DOCTYPE html
						PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
					<html>

					<head>
						<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
						<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
						<script src="/webjars/jquery/jquery.min.js"></script>
						<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>

						<ocaParchis:layout pageName="home">
							<title>Match ${match.id}</title>
					</head>

					<body style="background-color:#ececec">
						<h2 style="font-family:monospace">${match.playerToPlay.user.login}'s turn</h2>
						<c:forEach items="${match.playerStuck}" var="stuckplayer">
							<h2 style="font-family:monospace">
								<c:out value="${stuckplayer.user.login}'s turn was skipped..."></c:out>
							</h2>
						</c:forEach>
						<c:if test="${match.lastRoll!=0 && match.lastRoll!= -1}">
							<h2 style="font-family:monospace">${prevPlayer.user.login} rolled a <span
									style="color:#d9534f">${match.lastRoll}</span>!!</h2>
							<c:if test="${prevPlayer.position!=1}">
								<h2 style="font-family:monospace">${prevPlayer.user.login} fell in a <span
										style="color:#d9534f">${allTiles[prevPlayer.position-1].type.name}</span>!!</h2>
							</c:if>
							<c:if test="${prevPlayer.position==1}">
								<h2 style="font-family:monospace">Oh no!! ${prevPlayer.user.login} died and was sent to
									the start!!</h2>
							</c:if>
							<c:if test="${allTiles[prevPlayer.position-1].type.name=='OCA' && prevPlayer.position!=1}">
								<h2 style="font-family:monospace">${prevPlayer.user.login} went from tile <span
										style="color:#d9534f">${prevOca.id}</span> to <span style="color:#d9534f">
										${prevPlayer.position}</span>!!</h2>
							</c:if>
							<c:if test="${allTiles[prevPlayer.position-1].type.name=='BRIDGE'}">
								<h2 style="font-family:monospace">${prevPlayer.user.login} went from tile <span
										style="color:#d9534f">${otherBridge}</span> to <span style="color:#d9534f">
										${prevPlayer.position}</span>!!</h2>
							</c:if>
							<c:if test="${allTiles[prevPlayer.position-1].type.name=='DICE'}">
								<h2 style="font-family:monospace">${prevPlayer.user.login} went from tile <span
										style="color:#d9534f">${otherDice}</span> to <span style="color:#d9534f">
										${prevPlayer.position}</span>!!</h2>
							</c:if>
							<div style="text-align: center;">
								<spring:url value="/resources/images/dice${match.lastRoll}.PNG" htmlEscape="true"
									var="diceNumber" />
								<img src="${diceNumber}" style="margin: 30px;" />
							</div>
						</c:if>
						<c:if test="${match.lastRoll== -1}">
							<h2 style="font-family:monospace">${prevPlayer.user.login} turn was skipped...</h2>
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
									<td>
										<c:out value="${playerstats.id}" />
									</td>
									<td>
										<c:out value="${playerstats.user.login}" />
									</td>
									<td>
										<c:out value="${playerstats.position}" />
									</td>
									<td>
										<c:out value="${playerstats.numDiceRolls}" />
									</td>
									<td>
										<c:out value="${playerstats.turnsStuck}" />
									</td>
									<c:if test="${playerstats.position!=1}">
										<td>
											<c:out value="${allTiles[playerstats.position-1].type.name}" />
										</td>
									</c:if>
									<c:if test="${playerstats.position==1}">
										<td>
											<c:out value="DEATH" />
										</td>
									</c:if>
								</tr>
							</c:forEach>
							<c:if test="${match.playerToPlay.user==loggedUser}">
								<c:if test="${match.playerToPlay.turnsStuck==0}">
									<a class="btn btn-danger" href="/matches/${match.id}/advance"> Throw
										Dice!</a><br><br>
								</c:if>
								<c:if test="${match.playerToPlay.turnsStuck!=0}">
									<a class="btn btn-danger" href="/matches/${match.id}/advance"> Skip Turn...</a><br><br>
								</c:if>
							</c:if>
						</table>
					</body>
					</ocaParchis:layout>

					</html>