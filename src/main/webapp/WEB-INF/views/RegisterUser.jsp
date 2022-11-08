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

<title>Register User</title>
</head>
<body>
	<h2>Register User:</h2>
	<mvc:form modelAttribute="user">
		<table>
			<tr>
				<td><mvc:label path="login">Login:</mvc:label></td>
				<td><mvc:input path="login" /></td>
			</tr>
			<tr>
				<td><mvc:label path="password">Password:</mvc:label></td>
				<td><mvc:input path="password" /></td>
			</tr>
			<tr>
				<td><a href="/" class="btn btn-secondary">Cancel</a></td>
				<td><input type="submit" value="Save" class="btn btn-primary"/></td>
			</tr>
		</table>
	</mvc:form>
</body>
</html>