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

<title>Friends</title>
</head>
<body>
	<h2>Friends:</h2>
	<div class="container">
		<br />
		<c:if test="${message != null}">
		<div class="alert alert-${messageType}">
			<c:out value="${message}"></c:out>
			<a href="#" class="close" data-dismiss="alert" aria-label="close">�</a>
		</div>
		</c:if>
	</div>
	<a href="/">Go Back To Main Page</a><br>
	<a href="/friends/create"><span class="glyphicon glyphicon-plus sucess" aria-hidden="true"></span>Create Friend</a>
	<table class="table table-striped">
		<tr>			
            <th>Friend name</th>
			<th>solicitingUser</th>
			<th>Accepted?</th>
            <th>Date Accepted</th>
			<th>Status</th>
			<th>Delete friend</th>
			<th>Accept request?</th>

		</tr>
		<c:forEach items="${friends}" var="friend">
			<tr>	
				<c:if test="${loggedUser.equals(friend.user1)}">		
				<td><c:out value="${friend.user2.login}"/></td>
				</c:if>
				<c:if test="${loggedUser.equals(friend.user2)}">		
				<td><c:out value="${friend.user1.login}"/></td>
				</c:if>
				<td><c:out value="${friend.solicitingUser.login}"/></td>				
				<td><c:out value="${friend.accept}"/></td>				
                <td><c:out value="${friend.dateF}"/></td>
				<c:if test="${loggedUser.equals(friend.user1)}">		
				<td><c:out value="${friend.user2.userStatus}"/></td>
				</c:if>
				<c:if test="${loggedUser.equals(friend.user2)}">		
				<td><c:out value="${friend.user1.userStatus}"/></td>
				</c:if>
				<td><a href="/friends/delete/${friend.id}"><span class="glyphicon glyphicon-trash alert" aria-hidden="true"></a> </td>
				<c:if test="${friend.accept==false}">

				<td><span class="glyphicon glyphicon-plus sucess" aria-hidden="true"></span></td>
			</c:if>
			</tr>
		</c:forEach>
	</table>
</body>
</html>