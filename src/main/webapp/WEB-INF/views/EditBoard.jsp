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

<title>Board</title>
</head>
<body>
	<h2>Board</h2>
	<mvc:form modelAttribute="board" action="/boards/save">
		<table>
			<tr>
				<td><mvc:label path="id">ID</mvc:label></td>
				<td><mvc:input path="id" readOnly="true"/></td>
			</tr>
			<tr>
				<td><mvc:label path="description">Description:</mvc:label></td>
				<td><mvc:input path="description" /></td>
			</tr>
			<tr>
				<td><mvc:label path="shortname">Short name:</mvc:label></td>
				<td><mvc:input path="shortname" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Save" /></td>
			</tr>
		</table>
	</mvc:form>

</body>
</html>