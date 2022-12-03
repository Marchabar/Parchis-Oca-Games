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
	<title>Users</title>
</head>
<body style="background-color:#ececec">
	<h2 style="font-family:monospace">Users:</h2>
	<div class="container">
		<br />
		<c:if test="${message != null}">
		<div class="alert alert-${messageType}">
			<c:out value="${message}"></c:out>
			<a href="#" class="close" data-dismiss="alert" aria-label="close"> </a>
		</div>
		</c:if>
	</div>
	<a class="btn btn-danger" href="/users/create"><span class="glyphicon glyphicon-plus sucess" aria-hidden="true"></span>Create User</a>
	<table class="table table-striped">
		<tr>			
			<th>UserId</th>
			<th>Login</th>
            <th>Password</th>
			<th>Status</th>
            <th>Role</th>
			<th>Actions</th>
		</tr>
		<c:forEach items="${users}" var="user">
			<tr>				
				<td><c:out value="${user.id}"/></td>				
				<td><c:out value="${user.login}"/></td>				
                <td><c:out value="${user.password}"/></td>				
				<td><c:out value="${user.userStatus}"/></td>	
                <td><c:out value="${user.role}"/></td>				
				<td><a href="/users/edit/${user.id}" style="color:#d9534f"><span class="glyphicon glyphicon-pencil warning" aria-hidden="true"></span></a>
					&nbsp;<a href="/users/delete/${user.id}"style="color:#d9534f"><span class="glyphicon glyphicon-trash alert" aria-hidden="true"></a> </td>
			</tr>
		</c:forEach>
	</table>
</body>
</ocaParchis:layout>

</html>