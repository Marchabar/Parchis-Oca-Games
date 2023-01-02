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
<title>Your friend list</title>
</head>
<body style="background-color:#ececec">
	<h2 style="font-family:monospace">Your friend list:</h2>
	<a class="btn btn-danger" href="/friends/create" ><span class="glyphicon glyphicon-plus sucess" aria-hidden="true"></span>Add Friend</a>
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
            <th>Friend name</th>
            <th>Accepted?</th>
            <th>Date Accepted</th>
           	<th>Status</th>
            <th>Delete friend</th>
				<c:if test="${pendingRequest==true}">
					<th>Accept request?</th>
				</c:if>
			<th>Spectate</th>


        </tr>
		<c:forEach items="${friends}" var="friend"  varStatus="status">
			<tr>	
				<c:if test="${loggedUser.equals(friend.user1)}">		
				<td><c:out value="${friend.user2.login}"/></td>
				</c:if>
				<c:if test="${loggedUser.equals(friend.user2)}">		
				<td><c:out value="${friend.user1.login}"/></td>
				</c:if>
				<c:if test="${friend.accept==true}">
					<td><span class="glyphicon glyphicon-ok" aria-hidden="true"></span></td>
				</c:if>
				<c:if test="${friend.accept==false}">
					<td><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></td>
				</c:if>
				<td><c:out value="${friend.dateF}"/></td>	
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
					<c:if test="${friend.accept==false &&friend.solicitingUser != loggedUser}">
						<td><a href="/friends/myfriends/accept/${friend.id}"><span class="glyphicon glyphicon-plus sucess" aria-hidden="true"></a></td>
					</c:if>
				<c:if test="${pendingRequest && friend.accept}">
						<td><c:out value="${}"/></td>
					</c:if>
					<c:if test="${friend.accept==false}">
					<td><c:out value="${}"/></td>
				</c:if>
					<c:if test="${activeMatches[status.index]!=null && friend.accept}">
<<<<<<< HEAD
						<td><a href="/matches/${activeMatches[status.index].id}" style="color:#d9534f"><span class="glyphicon glyphicon-play-circle"></a> </td>	
=======
						<td><a href="/matches/${activeMatches[status.index].id}" style="color:#d9534f"><span class="glyphicon glyphicon-eye-open"></a> </td>	
>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e
						</c:if>
			<c:if test="${activeMatches[status.index]==null  && friend.accept}">
				<td><c:out value="Not in game"/></td>
			</c:if>
			</tr>
		</c:forEach>
	</table>
</body>
</ocaParchis:layout>
</html>