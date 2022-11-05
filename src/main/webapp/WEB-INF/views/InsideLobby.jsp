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

<title>Lobby ${lobby.id} </title>
</head>
<body>
	<h2>Lobby ${lobby.id}</h2>
	<mvc:form modelAttribute="lobby">
		<table>
			<tr>
				<td><mvc:label path="id">ID</mvc:label></td>
				<td><mvc:input path="id" readOnly="true"/></td>
			</tr>
			<tr>
				<td>---------------------</td>
			</tr>
			<tr>
				<td><mvc:label path="game">Game</mvc:label></td>
				<td><mvc:input path="game" readOnly="true"/></td>
			</tr>
			<tr>
				<td>---------------------</td>
			</tr>
			<tr>
			<td><b>Players:</b></td>
			</tr>
			<c:forEach items="${players}" var="player">
				<c:if test = "${lobby.host.login == player.login}">
				<tr>
					<td><b><c:out value="-${player.login}"/> THE HOST!!</b></td>	 
				</tr>
			</c:if>
			<c:if test = "${lobby.host.login != player.login}">
				<tr>
					<td><c:out value="-${player.login}"/></td>	 
				</tr>
			</c:if>
			</c:forEach>
		</table>
	</mvc:form>
</body>
</html>