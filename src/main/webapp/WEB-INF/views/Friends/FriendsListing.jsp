<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ocaParchis" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ocaParchis" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"  href="/webjars/bootstrap/css/bootstrap.min.css" />
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>

<ocaParchis:layout pageName="home">
<title>Friends</title>
</head>
<body style="background-color:#ececec">
	<h2 style="font-family:monospace">Friends:</h2>
	<table class="table table-striped">
		<tr>			
			<th>Friendship Id</th>
			<th>User 1</th>
            <th>User 2</th>
			<th>User who sent request</th>
			<th>Accepted?</th>
            <th>Date Accepted</th>
			<th>Actions</th>
		</tr>
		<c:forEach items="${friends}" var="friend">
			<tr>				
				<td><c:out value="${friend.id}"/></td>				
				<td><c:out value="${friend.user1.login}"/></td>				
                <td><c:out value="${friend.user2.login}"/></td>
				<td><c:out value="${friend.solicitingUser.login}"/></td>				
				<td><c:out value="${friend.accept}"/></td>				
                <td><c:out value="${friend.dateF}"/></td>	
				<td><a href="/friends/edit/${friend.id}" style="color:#d9534f"><span class="glyphicon glyphicon-pencil warning" aria-hidden="true"></span></a>
					&nbsp;<a href="/friends/delete/${friend.id}" style="color:#d9534f"><span class="glyphicon glyphicon-trash alert" aria-hidden="true"></a> </td>

			</tr>
		</c:forEach>
	</table>
</body>
</ocaParchis:layout>
</html>