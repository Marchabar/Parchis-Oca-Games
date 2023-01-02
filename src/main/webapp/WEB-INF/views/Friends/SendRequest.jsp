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
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<<< HEAD:src/main/webapp/WEB-INF/views/Friends/SendRequest.jsp
<title>Add Friend</title>
</head>
<body style="background-color:#ececec">
	<h2 style="font-family:monospace">Add Friend:</h2>
	<mvc:form modelAttribute="friend">
		<ocaParchis:inputField label="Enter friend's username" name="user2.login"/>
		<a href="/friends/myfriends" class="btn btn-secondary" style="color:#d9534f">Cancel</a>
		<input type="submit" value="Save" class="btn btn-danger"/>
	</mvc:form>
</body>
</ocaParchis:layout>
========
=======
<<<<<<<< HEAD:src/main/webapp/WEB-INF/views/Lobbies/EditLobby.jsp
>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e
	<title>Edit Lobby</title>
</head>
<body style="background-color:#ececec">
	<h2 style="font-family:monospace">Edit Lobby:</h2>
	<mvc:form modelAttribute="lobby">
			<div class="control-group">
				<ocaParchis:selectField name="game" label="Games" names="${games}" size="2"/>
			</div>
			<td><a href="/lobbies/${game}" class="btn btn-secondary" style="color:#d9534f">Cancel</a></td>
			<td><input type="submit" value="Save" class="btn btn-danger"/></td>
	</mvc:form>
</body>
</ocaParchis:layout>

<<<<<<< HEAD
>>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e:src/main/webapp/WEB-INF/views/Lobbies/EditLobby.jsp
=======
========
=======
>>>>>>> 5af423dd41bc285202b4e6654427cf45202ed9e0
<title>Add Friend</title>
</head>
<body style="background-color:#ececec">
	<h2 style="font-family:monospace">Add Friend:</h2>
	<mvc:form modelAttribute="friend">
		<ocaParchis:inputField label="Enter friend's username" name="user2.login"/>
		<a href="/" class="btn btn-secondary" style="color:#d9534f">Cancel</a>
		<input type="submit" value="Save" class="btn btn-danger"/>
	</mvc:form>
</body>
</ocaParchis:layout>
<<<<<<< HEAD
>>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e:src/main/webapp/WEB-INF/views/Friends/SendRequest.jsp
>>>>>>> 6d2d017d4c75e58175271779b56721445891cb6e
=======
>>>>>>> 5af423dd41bc285202b4e6654427cf45202ed9e0
</html>