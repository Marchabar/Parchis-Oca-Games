<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="mvc"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>

<title>Edit Friend</title>
</head>
<body>
	<h2>Edit Friend:</h2>
	<mvc:form modelAttribute="friend">
		<table>
			<tr>
				<td><mvc:label path="id">ID</mvc:label></td>
				<td><mvc:input path="id" readOnly="true"/></td>
			</tr>
			<tr>
				<td><mvc:label path="user1">user1:</mvc:label></td>
				<td><mvc:input path="user1" /></td>
			</tr>
			<tr>
				<td><mvc:label path="user2">user2:</mvc:label></td>
				<td><mvc:input path="user2" /></td>
			</tr>
			<tr>
				<td><mvc:label path="solicitingUser">solicitingUser:</mvc:label></td>
				<td><mvc:input path="solicitingUser" readOnly="true"/></td>
			</tr>
			<tr>
				<td><mvc:label path="accept">accept:</mvc:label></td>
				<td><mvc:input path="accept" /></td>
			</tr>
			<tr>
				<td><mvc:label path="dateF">date:</mvc:label></td>
				<td><mvc:input path="dateF" /></td>
			</tr>
			<tr>
				<td><a href="/friends" class="btn btn-secondary">Cancel</a></td>
				<td><input type="submit" value="Save" class="btn btn-primary"/></td>
			</tr>
		</table>
	</mvc:form>
</body>
</html>