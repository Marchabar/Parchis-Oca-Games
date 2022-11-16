<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"  href="/webjars/bootstrap/css/bootstrap.min.css" />
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>

<title>Your friend list</title>
</head>
<body>
	<h2>Your friend list:</h2>
	<div class="container">
		<br />
		<c:if test="${message != null}">
		<div class="alert alert-${messageType}">
			<c:out value="${message}"></c:out>
			<a href="#" class="close" data-dismiss="alert" aria-label="close">�</a>
		</div>
		</c:if>
	</div>
	<a href="/">Go Back To Main Page</a><br><br>
	<a href="/friends/create"><span class="glyphicon glyphicon-plus sucess" aria-hidden="true"></span>Add Friend</a>
	<table class="table table-striped">
		<tr>
            <th>Friend name</th>
            <th>Accepted?</th>
            <th>Date Accepted</th>
           	<th>Status</th>
            <th>Delete friend</th>
			<c:forEach items="${friends}" var="friend">
				<c:if test="${friend.accept==false}">
					<c:if test="${friend.solicitingUser != loggedUser}">
						<th>Accept request?</th>
					</c:if>
				</c:if>
			</c:forEach>

        </tr>
		<c:forEach items="${friends}" var="friend">
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
						<td><c:out value="${friend.user2.userStatus}"/></td>
					</c:if>
					<c:if test="${loggedUser.equals(friend.user2)}">
						<td><c:out value="${friend.user1.userStatus}"/></td>
					</c:if>
				</c:if>
				<c:if test="${friend.accept==false}">
					<td><c:out value="${}"/></td>
				</c:if>
				<td><a href="/friends/delete/${friend.id}"><span class="glyphicon glyphicon-trash" aria-hidden="true"></a> </td>
				<c:if test="${friend.accept==false}">
					<c:if test="${friend.solicitingUser != loggedUser}">
						<td><a href="/friends/myfriends/accept/${friend.id}"><span class="glyphicon glyphicon-plus sucess" aria-hidden="true"></a></td>
					</c:if>
				</c:if>
				<c:if test="${friend.accept==true}">
					<c:if test="${friend.solicitingUser == loggedUser}">
						<td><c:out value="${}"/></td>
					</c:if>
				</c:if>
			</tr>
		</c:forEach>
	</table>
</body>
</html>