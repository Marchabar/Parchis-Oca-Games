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
	<title>Edit Message</title>
</head>
<body style="background-color:#ececec">
	<h2 style="font-family:monospace">Send Message:</h2>
	<mvc:form modelAttribute="newMessageChat">
		<div class="control-group">
		<ocaParchis:inputField label="Message" name="description"/>
		</div>
<<<<<<< HEAD
		<a href="/matches/${matchId}/chat" class="btn btn-secondary" style="color:#d9534f">Cancel</a>
=======
		<a href="/matches/${matchId}" class="btn btn-secondary" style="color:#d9534f">Cancel</a>
>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e
		<button type="submit" class="btn btn-danger">
			<span style="font-family:monospace">Send</span><span class="glyphicon glyphicon-chevron-right" style="margin-left:5px"></span>
		</button>        
	</mvc:form>
</body>
</ocaParchis:layout>

</html>