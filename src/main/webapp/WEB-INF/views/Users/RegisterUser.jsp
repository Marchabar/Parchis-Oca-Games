<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="mvc"%>
<%@ taglib prefix="ocaParchis" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>

<ocaParchis:layout pageName="home">
	<title>Register User</title>
</head>
<body style="background-color:#ececec">
	<h2 style="font-family:monospace">Register User:</h2>
	<mvc:form modelAttribute="user">
            <ocaParchis:inputField label="Login" name="login"/>
            <ocaParchis:inputField label="Password" name="password"/>
<<<<<<< HEAD
<<<<<<< HEAD
=======
			<td><mvc:input type="hidden" path="role" readOnly="true" value="member"/></td>
>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e
=======
			<td><mvc:input type="hidden" path="role" readOnly="true" value="member"/></td>
>>>>>>> 5af423dd41bc285202b4e6654427cf45202ed9e0
			<a href="/users" class="btn btn-secondary" style="color:#d9534f">Cancel</a>
			<input type="submit" value="Save" class="btn btn-danger"/>
	</mvc:form>
</body>
</ocaParchis:layout>
</html>