<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ocaParchis" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"  href="/webjars/bootstrap/css/bootstrap.min.css" />
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>

<ocaParchis:layout pageName="home">
	<title>Parchis and Oca</title>
	</head>
	<body style="background-color:#ececec">
		<h1 style="font-family:monospace" align="middle">&nbsp;Welcome to the Parchis and Oca games</h1>
		<c:if test="${loggedUser==null}">
			<div align="middle">
				<spring:url value="/resources/images/oca.jpg" htmlEscape="true" var="ocaImage"/>
				<a href="/lobbies/oca"><img src="${ocaImage}" style="margin: 30px; border: 3px solid #d9534f" width="500" height="500"/></a>
				<spring:url value="/resources/images/parchis.png" var="parchisImage"/>
				<a href="/lobbies/parchis"><img src="${parchisImage}"  style ="margin: 30px; border: 3px solid #d9534f" width="500" height="500"/></a>
			</div>	
		</c:if>
		<c:if test="${loggedUser!=null}">
			<div style="display: inline-block; width: 100%;">
				<div style="margin-left: 10%; float:left;width: 50%;position: absolute;left: 0;">
					<spring:url value="/resources/images/oca.jpg" htmlEscape="true" var="ocaImage"/>
					<a href="/lobbies/oca"><img src="${ocaImage}" style="margin: 30px; border: 3px solid #d9534f" width="400" height="400"/></a>
					<spring:url value="/resources/images/parchis.png" var="parchisImage"/>
					<a href="/lobbies/parchis"><img src="${parchisImage}"  style ="margin: 30px; border: 3px solid #d9534f" width="400" height="400"/></a>
				</div>	
				<div style="margin-right: 10%; margin-top: 30px; width: 30%;height: 65%;  position: absolute;right: 0;overflow-y: scroll;scroll-behavior: smooth; background-color: #e6e6e6; border: 2px #222222; border-style:solid;float:left ">
					<table class="table table-striped" style="width: 100%;table-layout: auto;">
						<tr>
							<a class="btn btn-danger" href="/friends/create" ><span class="glyphicon glyphicon-plus sucess" aria-hidden="true"></span>Add Friend</a>
						</tr>
						<tr>
							<th>Friend name</th>
							<th>Status</th>
							<th>Delete friend</th>
							<th>Spectate</th>
							<th>Join Lobby</th>
						</tr>
						<c:forEach items="${friends}" var="friend"  varStatus="status">
							<c:if test="${friend.accept!=false}">
								<tr>	
									<c:if test="${loggedUser.equals(friend.user1)}">		
									<td><c:out value="${friend.user2.login}"/></td>
									</c:if>
									<c:if test="${loggedUser.equals(friend.user2)}">		
									<td><c:out value="${friend.user1.login}"/></td>
									</c:if>
									<c:if test="${friend.accept==true}">
										<c:if test="${loggedUser.equals(friend.user1)}">
											<c:if test="${friend.user2.userStatus.id == 1}">
												<td style="color: #4a9721"><c:out value="${friend.user2.userStatus}"/></td>
											</c:if>
											<c:if test="${friend.user2.userStatus.id == 2}">
												<td style="color: #6f6f6f"><c:out value="${friend.user2.userStatus}"/></td>
											</c:if>
											<c:if test="${friend.user2.userStatus.id == 3}">
												<td style="color: #978721"><c:out value="${friend.user2.userStatus}"/></td>
											</c:if>
										</c:if>
										<c:if test="${loggedUser.equals(friend.user2)}">
											<c:if test="${friend.user1.userStatus.id == 1}">
												<td style="color: #4a9721"><c:out value="${friend.user1.userStatus}"/></td>
											</c:if>
											<c:if test="${friend.user1.userStatus.id == 2}">
												<td style="color: #6f6f6f"><c:out value="${friend.user1.userStatus}"/></td>
											</c:if>
											<c:if test="${friend.user1.userStatus.id == 3}">
												<td style="color: #978721"><c:out value="${friend.user1.userStatus}"/></td>
											</c:if>
										</c:if>
									</c:if>
									<c:if test="${friend.accept==false}">
										<td><c:out value="${}"/></td>
									</c:if>
									<td><a href="/friends/delete/${friend.id}" style="color:#d9534f"><span class="glyphicon glyphicon-trash" aria-hidden="true"></a> </td>
									<c:if test="${activeMatches[status.index]!=null && friend.accept}">
										<td><a href="/matches/${activeMatches[status.index].id}" style="color:#d9534f"><span class="glyphicon glyphicon-eye-open"></a> </td>	
									</c:if>
									<c:if test="${activeMatches[status.index]==null  && friend.accept}">
										<td><c:out value="Not in game"/></td>
									</c:if>
									<c:if test="${AvailableLobbies[status.index]==0 && friend.accept}">
										<td><c:out value="Not available lobby"/></td>
									</c:if>
									<c:if test="${AvailableLobbies[status.index]!=0 && friend.accept}">
										<td><a href="/lobbies/${AvailableLobbies[status.index]}" style="color:#d9534f"><span class="glyphicon glyphicon-play-circle"></a> </td>	
									</c:if>
									<c:if test="${friend.accept==false}">
										<td><c:out value="${}"/></td>
									</c:if>
								</tr>
							</c:if>
						</c:forEach>
					</table>
				</div>
			</div>
		</c:if>
	</body>
</ocaParchis:layout>
</html>