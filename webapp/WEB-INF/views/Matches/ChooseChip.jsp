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
                            <c:if test="${match.game.name=='Parchis'}">
                                <c:out value="${match.lastRoll}"></c:out>
                                <c:out value="${match.event}"></c:out>
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


                                <table class="table table-striped">
                                    <tr>
                                        <c:forEach items="${allParchisTiles}" var="parchistile">

                                            <c:if test="${(parchistile.type.id == 1) or (parchistile.type.id == 5)}">
                                                <div class="col-md-4" style="background:rgb(250, 117, 110);background-size:cover;width: 65px
														;height: 80px;border: solid 1px rgb(0, 0, 0); position:relative">
                                                    <c:forEach items="${match.playerStats}" var="playerstats">
                                                        <c:forEach items="${playerstats.chips}" var="chip">
                                                            <c:if test="${chip.relativePosition == parchistile.id}">
                                                                <spring:url
                                                                    value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
                                                                    htmlEscape="true" var="chipColor" />
                                                                    <a href="/matches/${match.id}/chooseChip/${chip.id}"><img src="${chipColor}"
                                                                        width="26px" height="26px" ></a>

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
                                                        <span style="background:white;height: 20px;width: 30px;line-height:20px;
																display: inline-block;text-align: center; font-family:monospace; 
																border: 2px solid rgb(0, 0, 0);margin-top: 3px; 
																font-size: 10px;">SAFE</span>
                                                    </c:if>
                                                </div>
                                            </c:if>

                                            <c:if test="${(parchistile.type.id == 2) or (parchistile.type.id == 6)}">
                                                <div class="col-md-4" style="background:rgb(110, 138, 250);background-size:cover;width: 65px
													;height: 80px;border: solid 1px rgb(0, 0, 0); position:relative">
                                                    <c:forEach items="${match.playerStats}" var="playerstats">
                                                        <c:forEach items="${playerstats.chips}" var="chip">
                                                            <c:if test="${chip.relativePosition == parchistile.id}">
                                                                <spring:url
                                                                    value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
                                                                    htmlEscape="true" var="chipColor" />
                                                                    <a href="/matches/${match.id}/chooseChip/${chip.id}"><img src="${chipColor}"
                                                                        width="26px" height="26px"></a>
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
                                                        <span style="background:white;height: 20px;width: 30px;line-height:20px;
																display: inline-block;text-align: center; font-family:monospace; 
																border: 2px solid rgb(0, 0, 0);margin-top: 3px; 
																font-size: 10px;">SAFE</span>
                                                    </c:if>
                                                </div>
                                            </c:if>

                                            <c:if test="${(parchistile.type.id == 3) or (parchistile.type.id == 7)}">
                                                <div class="col-md-4" style="background:rgb(110, 250, 129);background-size:cover;width: 65px
													;height: 80px;border: solid 1px rgb(0, 0, 0); position:relative">
                                                    <c:forEach items="${match.playerStats}" var="playerstats">
                                                        <c:forEach items="${playerstats.chips}" var="chip">
                                                            <c:if test="${chip.relativePosition == parchistile.id}">
                                                                <spring:url
                                                                    value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
                                                                    htmlEscape="true" var="chipColor" />
                                                                    <a href="/matches/${match.id}/chooseChip/${chip.id}"><img src="${chipColor}"
                                                                        width="26px" height="26px"></a>
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
                                                        <span style="background:white;height: 20px;width: 30px;line-height:20px;
																display: inline-block;text-align: center; font-family:monospace; 
																border: 2px solid rgb(0, 0, 0);margin-top: 3px; 
																font-size: 10px;">SAFE</span>
                                                    </c:if>
                                                </div>
                                            </c:if>

                                            <c:if test="${(parchistile.type.id == 4) or (parchistile.type.id == 8)}">
                                                <div class="col-md-4" style="background:rgb(250, 248, 110);background-size:cover;width: 65px
													;height: 80px;border: solid 1px rgb(0, 0, 0); position:relative">
                                                    <c:forEach items="${match.playerStats}" var="playerstats">
                                                        <c:forEach items="${playerstats.chips}" var="chip">
                                                            <c:if test="${chip.relativePosition == parchistile.id}">
                                                                <spring:url
                                                                    value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
                                                                    htmlEscape="true" var="chipColor" />
                                                                    <a href="/matches/${match.id}/chooseChip/${chip.id}"><img src="${chipColor}"
                                                                        width="26px" height="26px"></a>
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
                                                        <span style="background:white;height: 20px;width: 30px;line-height:20px;
																display: inline-block;text-align: center; font-family:monospace; 
																border: 2px solid rgb(0, 0, 0);margin-top: 3px; 
																font-size: 10px;">SAFE</span>
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
                                                                <c:if test="${chip.absolutePosition+5 == parchistile.id && chip.chipColor.id==1}">
                                                                    <spring:url
                                                                        value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
                                                                        htmlEscape="true" var="chipColor" />
                                                                        <a href="/matches/${match.id}/chooseChip/${chip.id}"><img src="${chipColor}"
                                                                            width="26px" height="26px"></a>
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
                                                                <c:if test="${chip.absolutePosition+13 == parchistile.id && chip.chipColor.id==2}">
                                                                    <spring:url
                                                                        value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
                                                                        htmlEscape="true" var="chipColor" />
                                                                        <a href="/matches/${match.id}/chooseChip/${chip.id}"><img src="${chipColor}"
                                                                            width="26px" height="26px"></a>
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
                                                                <c:if test="${chip.absolutePosition+21 == parchistile.id && chip.chipColor.id==3}">
                                                                    <spring:url
                                                                        value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
                                                                        htmlEscape="true" var="chipColor" />
                                                                        <a href="/matches/${match.id}/chooseChip/${chip.id}"><img src="${chipColor}"
                                                                            width="26px" height="26px"></a>
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
                                                                <c:if test="${chip.absolutePosition+29 == parchistile.id && chip.chipColor.id==4}">
                                                                    <spring:url
                                                                        value="/resources/images/chips/${playerstats.user.prefColor.name}.png"
                                                                        htmlEscape="true" var="chipColor" />
                                                                        <a href="/matches/${match.id}/chooseChip/${chip.id}"><img src="${chipColor}"
                                                                            width="26px" height="26px"></a>
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

                        </div>
                        <div
                            style="width: 20%; height: 100%;  position: fixed;bottom: 0;left: 72%; right: 0%;overflow-y: scroll;scroll-behavior: smooth; background-color: #e6e6e6; border: 2px #222222; border-style:solid ">
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