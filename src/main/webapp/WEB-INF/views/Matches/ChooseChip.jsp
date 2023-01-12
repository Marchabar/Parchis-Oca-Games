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
						<div style="position: absolute;left: 10%;right:30%;">
							<h2 style="font-family:monospace">${match.playerToPlay.user.login}'s turn</h2>
							<c:if test="${match.lastRoll<=6 && match.lastRoll>0}">
									<c:if test="${!match.event.contains(match.playerToPlay.user.login)}">
										<h2 style="font-family:monospace">${prevPlayer.user.login} rolled a
											<span style="color:#d9534f">${match.lastRoll}</span>!!
										</h2>
									</c:if>
									<c:if test="${match.event.contains(match.playerToPlay.user.login)}">
										<h2 style="font-family:monospace">${match.playerToPlay.user.login} rolled a
											<span style="color:#d9534f">${match.lastRoll}</span>!!
										</h2>
									</c:if>
									<div style="text-align: center;">
										<spring:url value="/resources/images/diceImages/dice${match.lastRoll}.PNG"
											htmlEscape="true" var="diceNumber" />
										<img src="${diceNumber}" style="margin: 50px;" width=100px height=100px />
									</div>
								</c:if>
								<c:if test="${match.lastRoll>6}">
									<c:if test="${!match.event.contains(match.playerToPlay.user.login)}">
										<h2 style="font-family:monospace">${prevPlayer.user.login} has
											<span style="color:#d9534f">${match.lastRoll} extra tiles</span>!!
										</h2>
									</c:if>
									<c:if test="${match.event.contains(match.playerToPlay.user.login)}">
										<h2 style="font-family:monospace">${match.playerToPlay.user.login} has
											<span style="color:#d9534f">${match.lastRoll} extra tiles</span>!!
										</h2>
									</c:if>
								</c:if>
								<c:if test="${match.lastRoll==-100}">
									<c:if test="${!match.event.contains(match.playerToPlay.user.login)}">
										<h2 style="font-family:monospace">${match.event}
										</h2>
									</c:if>
									<c:if test="${match.event.contains(match.playerToPlay.user.login)}">
										<h2 style="font-family:monospace">${match.playerToPlay.user.login} rolled three 6 in a row, they are a cheater!!</h2>
										<h2 style="font-family:monospace">${match.playerToPlay.user.login} is choosing
											which
											chip to
											<span style="color:#d9534f"> lose</span>!!
										</h2>
									</c:if>
								</c:if>
									<c:if test="${match.lastRoll!=-100}">
										<h2 style="font-family:monospace">${match.event}
										</h2>
									</c:if>
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
												<span style="color:${playerstats.user.prefColor.rgb}">
													<c:out value="${playerstats.user.login}" />
												</span>
											</td>
											<c:forEach items="${playerstats.chips}" var="chip">
												<td>
													<c:if test="${chip.relativePosition==0}">
														<c:out value="Home" />
													</c:if>
													<c:if
														test="${chip.absolutePosition<64 && chip.relativePosition!=0}">
														<c:out value="${chip.relativePosition}" />
													</c:if>
													<c:if
														test="${chip.absolutePosition>=64 && chip.relativePosition!=0 && chip.absolutePosition!=71}">
														<c:if test="${chip.chipColor.id==1}">
															<c:out value="${chip.absolutePosition+5}" />
														</c:if>
														<c:if test="${chip.chipColor.id==2}">
															<c:out value="${chip.absolutePosition+13}" />
														</c:if>
														<c:if test="${chip.chipColor.id==3}">
															<c:out value="${chip.absolutePosition+21}" />
														</c:if>
														<c:if test="${chip.chipColor.id==4}">
															<c:out value="${chip.absolutePosition+29}" />
														</c:if>
													</c:if>
													<c:if test="${chip.absolutePosition==71}">
														<c:out value="Finish!" />
													</c:if>
												</td>
											</c:forEach>
										</tr>
									</c:forEach>
								</table>

								<table class="table table-striped">
									<tr>
										<c:forEach items="${allParchisTiles}" var="parchistile">

											<c:if test="${(parchistile.type.id == 1) or (parchistile.type.id == 5)}">
												<div class="col-md-4" style="background:rgb(250, 117, 110);background-size:cover;width: 65px
														;height: 80px;border: solid 1px rgb(0, 0, 0); position:relative">
													<c:forEach items="${match.playerStats}" var="playerstats">
														<c:forEach items="${playerstats.chips}" var="chip">
															<c:if test="${chip.relativePosition == parchistile.id}">
																<c:if
																	test="${loggedUser.prefColor==chip.chipColor && usersInside.contains(loggedUser)}">
																	<spring:url
																		value="/resources/images/chips/${playerstats.user.prefColor.name}H.png"
																		htmlEscape="true" var="chipColor" />
																</c:if>
																<c:if test="${loggedUser.prefColor!=chip.chipColor}">
																	<spring:url
																		value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
																		htmlEscape="true" var="chipColor" />
																</c:if>
																<a href="/matches/${match.id}/chooseChip/${chip.id}"><img
																		src="${chipColor}" width="26px"
																		height="26px"></a>
															</c:if>
														</c:forEach>
													</c:forEach>
													<span style="background:white;border-radius:50%;height: 20px;width: 20px;line-height:20px;
																display: inline-block;text-align: center; font-family:monospace; 
																border: 2px solid rgb(0, 0, 0);margin-top: 8px; 
																font-size: 12px;"> ${parchistile.id}</span>
													<c:if test="${parchistile.type.id == 5}">
														<span style="background:rgb(252, 55, 55);height: 20px;width: 70px;line-height:20px;
																display: inline-block;text-align: center; font-family:monospace; 
																border: 2px solid rgb(0, 0, 0);margin-top: 3px; 
																font-size: 10px;">RED START</span>
													</c:if>
													<c:if test="${parchistile.safe == true}">
														<span style="background:white;height: 20px;width: 50px;line-height:20px;
																display: inline-block;text-align: center; font-family:monospace; 
																border: 2px solid rgb(0, 0, 0);margin-top: 3px; 
																font-size: 10px;">SAFE</span>
													</c:if>
													<c:if test="${parchistile.id == 34}">
														<span style="background:white;height: 15px;width: 50px;line-height:10px;
																display: inline-block;text-align: center; font-family:monospace; 
																border: 2px solid rgb(0, 0, 0);margin-top: 3px; 
																font-size: 8px;">->END</span>
													</c:if>
												</div>
											</c:if>

											<c:if test="${(parchistile.type.id == 2) or (parchistile.type.id == 6)}">
												<div class="col-md-4" style="background:rgb(110, 138, 250);background-size:cover;width: 65px
													;height: 80px;border: solid 1px rgb(0, 0, 0); position:relative">
													<c:forEach items="${match.playerStats}" var="playerstats">
														<c:forEach items="${playerstats.chips}" var="chip">
															<c:if test="${chip.relativePosition == parchistile.id}">
																<c:if
																	test="${loggedUser.prefColor==chip.chipColor && usersInside.contains(loggedUser)}">
																	<spring:url
																		value="/resources/images/chips/${playerstats.user.prefColor.name}H.png"
																		htmlEscape="true" var="chipColor" />
																</c:if>
																<c:if test="${loggedUser.prefColor!=chip.chipColor}">
																	<spring:url
																		value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
																		htmlEscape="true" var="chipColor" />
																</c:if>
																<a href="/matches/${match.id}/chooseChip/${chip.id}"><img
																		src="${chipColor}" width="26px"
																		height="26px"></a>
															</c:if>
														</c:forEach>
													</c:forEach>

													<span style="background:white;border-radius:50%;height: 20px;width: 20px;line-height:20px;
														display: inline-block;text-align: center; font-family:monospace; 
														border: 2px solid rgb(0, 0, 0);margin-top: 8px; 
														font-size: 12px;"> ${parchistile.id}</span>
													<c:if test="${parchistile.type.id == 6}">
														<span style="background:rgb(79, 70, 249);height: 20px;width: 70px;line-height:20px;
																display: inline-block;text-align: center; font-family:monospace; 
																border: 2px solid rgb(0, 0, 0);margin-top: 3px; 
																font-size: 10px;">BLUE START</span>
													</c:if>
													<c:if test="${parchistile.safe == true}">
														<span style="background:white;height: 20px;width: 50px;line-height:20px;
																display: inline-block;text-align: center; font-family:monospace; 
																border: 2px solid rgb(0, 0, 0);margin-top: 3px; 
																font-size: 10px;">SAFE</span>
													</c:if>
													<c:if test="${parchistile.id == 51}">
														<span style="background:white;height: 15px;width: 50px;line-height:10px;
																display: inline-block;text-align: center; font-family:monospace; 
																border: 2px solid rgb(0, 0, 0);margin-top: 3px; 
																font-size: 8px;">->END</span>
													</c:if>
												</div>
											</c:if>

											<c:if test="${(parchistile.type.id == 3) or (parchistile.type.id == 7)}">
												<div class="col-md-4" style="background:rgb(110, 250, 129);background-size:cover;width: 65px
													;height: 80px;border: solid 1px rgb(0, 0, 0); position:relative">
													<c:forEach items="${match.playerStats}" var="playerstats">
														<c:forEach items="${playerstats.chips}" var="chip">
															<c:if test="${chip.relativePosition == parchistile.id}">
																<c:if
																	test="${loggedUser.prefColor==chip.chipColor && usersInside.contains(loggedUser)}">
																	<spring:url
																		value="/resources/images/chips/${playerstats.user.prefColor.name}H.png"
																		htmlEscape="true" var="chipColor" />
																</c:if>
																<c:if test="${loggedUser.prefColor!=chip.chipColor}">
																	<spring:url
																		value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
																		htmlEscape="true" var="chipColor" />
																</c:if>
																<a href="/matches/${match.id}/chooseChip/${chip.id}"><img
																		src="${chipColor}" width="26px"
																		height="26px"></a>
															</c:if>
														</c:forEach>
													</c:forEach>
													<span style="background:white;border-radius:50%;height: 20px;width: 20px;line-height:20px;
														display: inline-block;text-align: center; font-family:monospace; 
														border: 2px solid rgb(0, 0, 0);margin-top: 8px; 
														font-size: 12px;"> ${parchistile.id}</span>
													<c:if test="${parchistile.type.id == 7}">
														<span style="background:rgb(46, 255, 53);height: 20px;width: 70px;line-height:20px;
																display: inline-block;text-align: center; font-family:monospace; 
																border: 2px solid rgb(0, 0, 0);margin-top: 3px; 
																font-size: 10px;">GREEN START</span>
													</c:if>
													<c:if test="${parchistile.safe == true}">
														<span style="background:white;height: 20px;width: 50px;line-height:20px;
																display: inline-block;text-align: center; font-family:monospace; 
																border: 2px solid rgb(0, 0, 0);margin-top: 3px; 
																font-size: 10px;">SAFE</span>
													</c:if>
													<c:if test="${parchistile.id == 17}">
														<span style="background:white;height: 15px;width: 50px;line-height:10px;
																display: inline-block;text-align: center; font-family:monospace; 
																border: 2px solid rgb(0, 0, 0);margin-top: 3px; 
																font-size: 8px;">->END</span>
													</c:if>
												</div>
											</c:if>

											<c:if test="${(parchistile.type.id == 4) or (parchistile.type.id == 8)}">
												<div class="col-md-4" style="background:rgb(250, 248, 110);background-size:cover;width: 65px
													;height: 80px;border: solid 1px rgb(0, 0, 0); position:relative">
													<c:forEach items="${match.playerStats}" var="playerstats">
														<c:forEach items="${playerstats.chips}" var="chip">
															<c:if test="${chip.relativePosition == parchistile.id}">
																<c:if
																	test="${loggedUser.prefColor==chip.chipColor && usersInside.contains(loggedUser)}">
																	<spring:url
																		value="/resources/images/chips/${playerstats.user.prefColor.name}H.png"
																		htmlEscape="true" var="chipColor" />
																</c:if>
																<c:if test="${loggedUser.prefColor!=chip.chipColor}">
																	<spring:url
																		value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
																		htmlEscape="true" var="chipColor" />
																</c:if>
																<a href="/matches/${match.id}/chooseChip/${chip.id}"><img
																		src="${chipColor}" width="26px"
																		height="26px"></a>
															</c:if>
														</c:forEach>
													</c:forEach>

													<span style="background:white;border-radius:50%;height: 20px;width: 20px;line-height:20px;
														display: inline-block;text-align: center; font-family:monospace; 
														border: 2px solid rgb(0, 0, 0);margin-top: 8px; 
														font-size: 12px;"> ${parchistile.id}</span>
													<c:if test="${parchistile.type.id == 8}">
														<span style="background:rgb(218, 239, 25);height: 20px;width: 70px;line-height:20px;
																display: inline-block;text-align: center; font-family:monospace; 
																border: 2px solid rgb(0, 0, 0);margin-top: 3px; 
																font-size: 10px;">YELLOW START</span>
													</c:if>
													<c:if test="${parchistile.safe == true}">
														<span style="background:white;height: 20px;width: 50px;line-height:20px;
																display: inline-block;text-align: center; font-family:monospace; 
																border: 2px solid rgb(0, 0, 0);margin-top: 3px; 
																font-size: 10px;">SAFE</span>
													</c:if>
													<c:if test="${parchistile.id == 68}">
														<span style="background:white;height: 15px;width: 50px;line-height:10px;
																display: inline-block;text-align: center; font-family:monospace; 
																border: 2px solid rgb(0, 0, 0);margin-top: 3px; 
																font-size: 8px;">->END</span>
													</c:if>
												</div>
											</c:if>
										</c:forEach>
									</tr>
								</table>

								<table class="table table-striped">
									<tr>
										<c:forEach items="${allParchisTiles}" var="parchistile">
											<c:if test="${(parchistile.type.id == 9) or (parchistile.type.id == 13)}">
												<div class="col-md-4" style="background:rgb(250, 117, 110);background-size:cover;width: 70px
														;height: 90px;border: solid 1px rgb(0, 0, 0); position:relative">
													<c:forEach items="${match.playerStats}" var="playerstats">
														<c:forEach items="${playerstats.chips}" var="chip">
															<c:if
																test="${chip.absolutePosition+5 == parchistile.id && chip.chipColor.id==1}">
																<c:if
																	test="${loggedUser.prefColor==chip.chipColor && usersInside.contains(loggedUser)}">
																	<spring:url
																		value="/resources/images/chips/${playerstats.user.prefColor.name}H.png"
																		htmlEscape="true" var="chipColor" />
																</c:if>
																<c:if test="${loggedUser.prefColor!=chip.chipColor}">
																	<spring:url
																		value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
																		htmlEscape="true" var="chipColor" />
																</c:if>
																<a href="/matches/${match.id}/chooseChip/${chip.id}"><img
																		src="${chipColor}" width="26px"
																		height="26px"></a>
															</c:if>
														</c:forEach>
													</c:forEach>
													<span style="background:white;border-radius:50%;height: 20px;width: 20px;line-height:20px;
																display: inline-block;text-align: center; font-family:monospace; 
																border: 2px solid rgb(0, 0, 0);margin-top: 8px; 
																font-size: 12px;"> ${parchistile.id}</span>
													<c:if test="${parchistile.type.id == 13}">
														<span style="background:rgb(252, 55, 55);height: 20px;width: 70px;line-height:20px;
																display: inline-block;text-align: center; font-family:monospace; 
																border: 2px solid rgb(0, 0, 0);margin-top: 3px; 
																font-size: 10px;">RED GOAL</span>
													</c:if>
												</div>
											</c:if>
										</c:forEach>
									</tr>
								</table>

								<table class="table table-striped">
									<tr>
										<c:forEach items="${allParchisTiles}" var="parchistile">
											<c:if test="${(parchistile.type.id == 10) or (parchistile.type.id == 14)}">
												<div class="col-md-4" style="background:rgb(110, 138, 250);background-size:cover;width: 70px
														;height: 90px;border: solid 1px rgb(0, 0, 0); position:relative">
													<c:forEach items="${match.playerStats}" var="playerstats">
														<c:forEach items="${playerstats.chips}" var="chip">
															<c:if
																test="${chip.absolutePosition+13 == parchistile.id && chip.chipColor.id==2}">
																<c:if
																	test="${loggedUser.prefColor==chip.chipColor && usersInside.contains(loggedUser)}">
																	<spring:url
																		value="/resources/images/chips/${playerstats.user.prefColor.name}H.png"
																		htmlEscape="true" var="chipColor" />
																</c:if>
																<c:if test="${loggedUser.prefColor!=chip.chipColor}">
																	<spring:url
																		value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
																		htmlEscape="true" var="chipColor" />
																</c:if>
																<a href="/matches/${match.id}/chooseChip/${chip.id}"><img
																		src="${chipColor}" width="26px"
																		height="26px"></a>
															</c:if>
														</c:forEach>
													</c:forEach>
													<span style="background:white;border-radius:50%;height: 20px;width: 20px;line-height:20px;
																display: inline-block;text-align: center; font-family:monospace; 
																border: 2px solid rgb(0, 0, 0);margin-top: 8px; 
																font-size: 12px;"> ${parchistile.id}</span>
													<c:if test="${parchistile.type.id == 14}">
														<span style="background:rgb(79, 70, 249);height: 20px;width: 70px;line-height:20px;
																display: inline-block;text-align: center; font-family:monospace; 
																border: 2px solid rgb(0, 0, 0);margin-top: 3px; 
																font-size: 10px;">BLUE GOAL</span>
													</c:if>
												</div>
											</c:if>
										</c:forEach>
									</tr>
								</table>

								<table class="table table-striped">
									<tr>
										<c:forEach items="${allParchisTiles}" var="parchistile">
											<c:if test="${(parchistile.type.id == 11) or (parchistile.type.id == 15)}">
												<div class="col-md-4" style="background:rgb(110, 250, 129);background-size:cover;width: 70px
														;height: 90px;border: solid 1px rgb(0, 0, 0); position:relative">
													<c:forEach items="${match.playerStats}" var="playerstats">
														<c:forEach items="${playerstats.chips}" var="chip">
															<c:if
																test="${chip.absolutePosition+21 == parchistile.id && chip.chipColor.id==3}">
																<c:if
																	test="${loggedUser.prefColor==chip.chipColor && usersInside.contains(loggedUser)}">
																	<spring:url
																		value="/resources/images/chips/${playerstats.user.prefColor.name}H.png"
																		htmlEscape="true" var="chipColor" />
																</c:if>
																<c:if test="${loggedUser.prefColor!=chip.chipColor}">
																	<spring:url
																		value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
																		htmlEscape="true" var="chipColor" />
																</c:if>
																<a href="/matches/${match.id}/chooseChip/${chip.id}"><img
																		src="${chipColor}" width="26px"
																		height="26px"></a>
															</c:if>
														</c:forEach>
													</c:forEach>
													<span style="background:white;border-radius:50%;height: 20px;width: 20px;line-height:20px;
																display: inline-block;text-align: center; font-family:monospace; 
																border: 2px solid rgb(0, 0, 0);margin-top: 8px; 
																font-size: 12px;"> ${parchistile.id}</span>
													<c:if test="${parchistile.type.id == 15}">
														<span style="background:rgb(46, 255, 53);height: 20px;width: 70px;line-height:20px;
																display: inline-block;text-align: center; font-family:monospace; 
																border: 2px solid rgb(0, 0, 0);margin-top: 3px; 
																font-size: 10px;">GREEN GOAL</span>
													</c:if>
												</div>
											</c:if>
										</c:forEach>
									</tr>
								</table>

								<table class="table table-striped">
									<tr>
										<c:forEach items="${allParchisTiles}" var="parchistile">
											<c:if test="${(parchistile.type.id == 12) or (parchistile.type.id == 16)}">
												<div class="col-md-4" style="background:rgb(250, 248, 110);background-size:cover;width: 70px
														;height: 90px;border: solid 1px rgb(0, 0, 0); position:relative">
													<c:forEach items="${match.playerStats}" var="playerstats">
														<c:forEach items="${playerstats.chips}" var="chip">
															<c:if
																test="${chip.absolutePosition+29 == parchistile.id && chip.chipColor.id==4}">
																<c:if
																	test="${loggedUser.prefColor==chip.chipColor && usersInside.contains(loggedUser)}">
																	<spring:url
																		value="/resources/images/chips/${playerstats.user.prefColor.name}H.png"
																		htmlEscape="true" var="chipColor" />
																</c:if>
																<c:if test="${loggedUser.prefColor!=chip.chipColor}">
																	<spring:url
																		value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
																		htmlEscape="true" var="chipColor" />
																</c:if>
																<a href="/matches/${match.id}/chooseChip/${chip.id}"><img
																		src="${chipColor}" width="26px"
																		height="26px"></a>
															</c:if>
														</c:forEach>
													</c:forEach>
													<span style="background:white;border-radius:50%;height: 20px;width: 20px;line-height:20px;
																display: inline-block;text-align: center; font-family:monospace; 
																border: 2px solid rgb(0, 0, 0);margin-top: 8px; 
																font-size: 12px;"> ${parchistile.id}</span>
													<c:if test="${parchistile.type.id == 16}">
														<span style="background:rgb(218, 239, 25);height: 20px;width: 70px;line-height:20px;
																display: inline-block;text-align: center; font-family:monospace; 
																border: 2px solid rgb(0, 0, 0);margin-top: 3px; 
																font-size: 10px;">YELLOW GOAL</span>
													</c:if>
												</div>
											</c:if>
										</c:forEach>
									</tr>
								</table>

						</div>
						<div
							style="margin-top: 2%;margin-right: 5%; height: 80%;  position: absolute;left: 72%; right: 0%;overflow-y: scroll;scroll-behavior: smooth; background-color: #e6e6e6; border: 2px #222222; border-style:solid ">
							<table class="table table-striped" style="width: 100%;table-layout: auto;">
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