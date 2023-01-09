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
														<spring:url
															value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
															htmlEscape="true" var="chipColor" />
														<img src="${chipColor}" width="26px" height="26px"></a>
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


						</c:if>
						</c:if>
						</div>




						<table>
							<tr>
								<td rowspan="8" colspan="8"></td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
							;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 35}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
							;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 34}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
								;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 33}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td rowspan="8" colspan="8"></td>
							</tr>
							<tr>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
								;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 36}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(46, 255, 53);background-size:cover;width: 70px
							;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 64 && chip.chipColor.id==3}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
								;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 32}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
							</tr>
							<tr>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
								;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 37}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(46, 255, 53);background-size:cover;width: 70px
							;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 65 && chip.chipColor.id==3}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
								;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 31}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
							</tr>
							<tr>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
								;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 38}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(46, 255, 53);background-size:cover;width: 70px
							;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 66 && chip.chipColor.id==3}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
								;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 30}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
							</tr>
							<tr>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
								;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 39}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(46, 255, 53);background-size:cover;width: 70px
							;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 67 && chip.chipColor.id==3}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
								;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 29}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
							</tr>
							<tr>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
								;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 40}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(46, 255, 53);background-size:cover;width: 70px
							;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 68 && chip.chipColor.id==3}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
								;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 28}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
							</tr>
							<tr>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
								;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 41}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(46, 255, 53);background-size:cover;width: 70px
							;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 69 && chip.chipColor.id==3}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
								;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 27}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
							</tr>
							<tr>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
								;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 42}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(46, 255, 53);background-size:cover;width: 70px
							;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 70 && chip.chipColor.id==3}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
								;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 26}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
							</tr>
							<tr>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
								;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 50}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
									;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 49}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
										;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 48}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
											;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 47}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
												;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 46}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
													;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 45}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
														;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 44}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
															;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 43}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td rowspan="3" colspan="3"></td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
								;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 25}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
									;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 24}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
										;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 23}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
											;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 22}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
												;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 21}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
													;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 20}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
														;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 19}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
															;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 18}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
							</tr>
							<tr>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
								;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 51}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(218, 239, 25);background-size:cover;width: 70px
						;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 64 && chip.chipColor.id==4}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(218, 239, 25);background-size:cover;width: 70px
						;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 65 && chip.chipColor.id==4}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(218, 239, 25);background-size:cover;width: 70px
						;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 66 && chip.chipColor.id==4}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(218, 239, 25);background-size:cover;width: 70px
						;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 67 && chip.chipColor.id==4}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(218, 239, 25);background-size:cover;width: 70px
						;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 68 && chip.chipColor.id==4}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(218, 239, 25);background-size:cover;width: 70px
						;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 69 && chip.chipColor.id==4}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(218, 239, 25);background-size:cover;width: 70px
						;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 70 && chip.chipColor.id==4}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(79, 70, 249);background-size:cover;width: 70px
						;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 70 && chip.chipColor.id==2}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(79, 70, 249);background-size:cover;width: 70px
						;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 69 && chip.chipColor.id==2}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(79, 70, 249);background-size:cover;width: 70px
						;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 68 && chip.chipColor.id==2}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(79, 70, 249);background-size:cover;width: 70px
						;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 67 && chip.chipColor.id==2}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(79, 70, 249);background-size:cover;width: 70px
						;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 66 && chip.chipColor.id==2}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(79, 70, 249);background-size:cover;width: 70px
						;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 65 && chip.chipColor.id==2}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(79, 70, 249);background-size:cover;width: 70px
						;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 64 && chip.chipColor.id==2}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
								;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 17}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
							</tr>
							<tr>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
								;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 52}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
									;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 53}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
										;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 54}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
											;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 55}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
									;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 56}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
										;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 57}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
											;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 58}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
												;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 59}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
													;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 9}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
														;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 10}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
															;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 11}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
																;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 12}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
																	;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 13}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
																		;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 14}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
																			;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 15}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
																				;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 16}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
							</tr>
							<tr>
								<td rowspan="8" colspan="8"></td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
									;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 60}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(252, 55, 55);background-size:cover;width: 70px
							;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 70 && chip.chipColor.id==1}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
									;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 8}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td rowspan="8" colspan="8"></td>
							</tr>
							<tr>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
									;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 61}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(252, 55, 55);background-size:cover;width: 70px
							;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 69 && chip.chipColor.id==1}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
									;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 7}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
							</tr>
							<tr>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
									;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 62}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(252, 55, 55);background-size:cover;width: 70px
							;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 68 && chip.chipColor.id==1}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
									;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 6}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
							</tr>
							<tr>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
									;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition ==63}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(252, 55, 55);background-size:cover;width: 70px
							;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 67 && chip.chipColor.id==1}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
									;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 5}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
							</tr>
							<tr>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
									;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 64}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(252, 55, 55);background-size:cover;width: 70px
							;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 66 && chip.chipColor.id==1}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
									;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 4}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
							</tr>
							<tr>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
									;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 65}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(252, 55, 55);background-size:cover;width: 70px
							;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 65 && chip.chipColor.id==1}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
									;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 3}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
							</tr>
							<tr>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
									;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 66}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(252, 55, 55);background-size:cover;width: 70px
							;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.absolutePosition == 64 && chip.chipColor.id==1}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
									;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 2}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
							</tr>
							<tr>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
									;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 67}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
										;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 68}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
								<td>
									<div class="col-md-4" style="background:rgb(255, 255, 255);background-size:cover;width: 70px
											;height: 45px;border: solid 1px rgb(0, 0, 0); position:relative">
										<c:forEach items="${match.playerStats}" var="playerstats">
											<c:forEach items="${playerstats.chips}" var="chip">
												<c:if test="${chip.relativePosition == 1}">
													<spring:url
														value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
														htmlEscape="true" var="chipColor" />
													<img src="${chipColor}" width="26px" height="26px">
												</c:if>
											</c:forEach>
										</c:forEach>
								</td>
							</tr>
						</table>
						</div>
					</body>
					</ocaParchis:layout>

					</html>