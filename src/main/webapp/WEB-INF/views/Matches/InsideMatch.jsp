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
						<c:if test="${match.game.name=='Oca'}">
							<c:if test="${match.lastRoll!=0 && match.lastRoll!= -1}">
								<div style="text-align: center;">
									<c:if test="${match.lastRoll<=6}">
										<spring:url value="/resources/images/diceImages/dice${match.lastRoll}.PNG"
											htmlEscape="true" var="diceNumber" />
										<img src="${diceNumber}" style="margin: 30px;" width=100px height=100px />
									</c:if>
									<c:if test="${match.lastRoll>6}">
										<spring:url value="/resources/images/diceImages/dice${match.lastRoll-6}.PNG"
											htmlEscape="true" var="diceNumber" />
										<img src="${diceNumber}" style="margin: 30px;" width=100px height=100px />
									</c:if>
								</div>
								<div>
									<c:if test="${match.lastRoll<=6}">
										<h2 style="font-family:monospace">${prevPlayer.user.login} rolled a <span
												style="color:#d9534f">${match.lastRoll}</span>!!</h2>
									</c:if>
									<c:if test="${match.lastRoll>6}">
										<h2 style="font-family:monospace">${prevPlayer.user.login} rolled a <span
												style="color:#d9534f">${match.lastRoll-6}</span>!!</h2>
									</c:if>
									<c:if test="${prevPlayer.position!=1 &&match.lastRoll<=6}">
										<h2 style="font-family:monospace">${prevPlayer.user.login} fell in a <span
												style="color:#d9534f">${allTiles[prevPlayer.position-1].type.name}</span>!!
										</h2>
									</c:if>
									<c:if test="${match.lastRoll>6}">
										<h2 style="font-family:monospace">${prevPlayer.user.login} fell in the <span
												style="color:#d9534f">${allTiles[42-1].type.name}</span> and went back
											to
											tile <span style="color:#d9534f">30</span>!!</h2>
									</c:if>
									<c:if test="${prevPlayer.position==1}">
										<h2 style="font-family:monospace">Oh no!! ${prevPlayer.user.login} died and was
											sent
											to
											the start!!</h2>
									</c:if>
									<c:if
										test="${allTiles[prevPlayer.position-1].type.name=='OCA' && prevPlayer.position!=1}">
										<h2 style="font-family:monospace">${prevPlayer.user.login} went from tile <span
												style="color:#d9534f">${prevOca.id}</span> to <span
												style="color:#d9534f">
												${prevPlayer.position}</span>!!</h2>
									</c:if>
									<c:if test="${allTiles[prevPlayer.position-1].type.name=='BRIDGE'}">
										<h2 style="font-family:monospace">${prevPlayer.user.login} went from tile <span
												style="color:#d9534f">${otherBridge}</span> to <span
												style="color:#d9534f">
												${prevPlayer.position}</span>!!</h2>
									</c:if>
									<c:if test="${allTiles[prevPlayer.position-1].type.name=='DICE'}">
										<h2 style="font-family:monospace">${prevPlayer.user.login} went from tile <span
												style="color:#d9534f">${otherDice}</span> to <span
												style="color:#d9534f">
												${prevPlayer.position}</span>!!</h2>
									</c:if>
								</div>
							</c:if>
							<c:if test="${match.lastRoll== -1}">
								<h2 style="font-family:monospace">${prevPlayer.user.login} turn was skipped...</h2>
							</c:if>

							<table class="table table-striped">
								<tr>
									<sec:authorize access="hasAuthority('admin')">
										<th>Id</th>
									</sec:authorize>
									<th>Username</th>
									<th>Position</th>
									<th>Dice Rolls</th>
									<th>Turns stuck</th>
									<th>Tile</th>
								</tr>
								<c:forEach items="${match.playerStats}" var="playerstats">
									<tr>
										<sec:authorize access="hasAuthority('admin')">
											<td>
												<c:out value="${playerstats.id}" />
											</td>
										</sec:authorize>

										<td>
											<span style="color:${playerstats.user.prefColor.rgb}">
												<c:out value="${playerstats.user.login}" />
											</span>
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

								<div style="display: inline-block; width: 100%;">
									<div style="float:left">
										<c:if test="${match.playerToPlay.user==loggedUser}">
											<c:if test="${match.playerToPlay.turnsStuck==0}">
												<a class="btn btn-danger" href="/matches/${match.id}/advanceOca"> Throw
													Dice!</a><br><br>
											</c:if>
											<c:if test="${match.playerToPlay.turnsStuck!=0}">
												<a class="btn btn-danger" href="/matches/${match.id}/advanceOca"> Skip
													Turn...</a><br><br>
											</c:if>
										</c:if>
									</div>
								</div>

							</table>
							<table class="table table-striped">
								<tr>
									<c:forEach items="${allTiles}" var="ocatile">
										<spring:url value="/resources/images/ocaTilesImages/${ocatile.id}.png"
											htmlEscape="true" var="tile" />
										<div class="col-md-4" style="background-image: url(${tile});background-size:cover;width: 90px
											;height: 120px;border: solid 1px rgb(0, 0, 0); position:relative">
											<span style="background:white;border-radius:50%;height: 20px;width: 20px;line-height:20px;
												display: inline-block;text-align: center; font-family:monospace; 
												border: 2px solid rgb(0, 0, 0);margin-top: 8px; 
												font-size: 12px;"> ${ocatile.id}</span>
											<c:forEach items="${match.playerStats}" var="playerstats">
												<c:if test="${playerstats.position == ocatile.id}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>

										</div>
									</c:forEach>
								</tr>
							</table>
						</c:if>
						<c:out value="${match.lastRoll}"></c:out>
						<c:out value="${match.event}"></c:out>
						<c:if test="${match.game.name=='Parchis'}">
							<table class="table table-striped">
								<tr>
									<th>Username</th>
									<th>Chip 1</th>
									<th>Chip 2</th>
									<th>Chip 3</th>
									<th>Chip 4</th>
								</tr>
								<c:forEach items="${match.playerStats}" var="playerstats">
									<tr>

										<td>
											<c:out value="${playerstats.user.login}" />
										</td>
										<td>
											<c:out
												value="${playerstats.chips[0].id}-${playerstats.chips[0].absolutePosition}-${playerstats.chips[0].relativePosition}" />
										</td>
										<td>
											<c:out
												value="${playerstats.chips[1].id}-${playerstats.chips[1].absolutePosition}-${playerstats.chips[1].relativePosition}" />
										</td>
										<td>
											<c:out
												value="${playerstats.chips[2].id}-${playerstats.chips[2].absolutePosition}-${playerstats.chips[2].relativePosition}" />
										</td>
										<td>
											<c:out
												value="${playerstats.chips[3].id}-${playerstats.chips[3].absolutePosition}-${playerstats.chips[3].relativePosition}" />
										</td>
									</tr>
								</c:forEach>
							</table>
							<c:if test="${match.playerToPlay.user==loggedUser}">
								<a class="btn btn-danger" href="/matches/${match.id}/advanceParchis"> Throw Dice!>
							</c:if>
						</c:if>
						<div
							style="width: 450px; height: 300px;  position: fixed;bottom: 0;right: 0;overflow-y: scroll;scroll-behavior: smooth; background-color: #e6e6e6; border: #000000; border-style:solid ">
							<table class="table table-striped">
								<tr>
									<th><a class="btn btn-danger" href="/matches/${match.id}/chat">See full chat</a>
									</th>
									<th><a class="btn btn-danger" href="/matches/${match.id}/chat/send">Write a
											message</a></th>
									<th></th>
								</tr>
								<c:forEach items="${messagesChat}" var="messagesChat">
									<tr>
										<c:if test="${usersInside.contains(messagesChat.user)}">
											<td><span style="color:${messagesChat.user.prefColor.rgb}">
													<c:out value="${messagesChat.user.login}" />
												</span></td>
										</c:if>
										<c:if test="${!usersInside.contains(messagesChat.user)}">
											<td><span style="color:#000000">
													<c:out value="${messagesChat.user.login}" />
												</span></td>
										</c:if>
										<td>
											<c:out value="${messagesChat.description}" />
										</td>
										<td>
											<c:out value="${messagesChat.time}" />
										</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</body>
					</ocaParchis:layout>

					</html>