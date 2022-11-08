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

<title>All stats for <c:out value="${user.login}"></c:out></title>
</head>
<body>
	<h2>All stats for <c:out value="${user.login}"></c:out></h2>
	<div class="container">
		<br />
		<c:if test="${message != null}">
		<div class="alert alert-${messageType}">
			<c:out value="${message}"></c:out>
			<a href="#" class="close" data-dismiss="alert" aria-label="close">�</a>
		</div>
		</c:if>
	</div>
	<a href="/">Go Back To Main Page</a>
	<table class="table table-striped">
		<tr>			
			<th>Match ID</th>
            <th>Winner</th>
			<th>Number of turns</th>
            <th>Number of dice rolls</th>
		</tr>
		<c:forEach items="${stats}" var="stat">
			<tr>				
				<td><c:out value="${stat.match.id}"/></td>		
                <td><c:out value="${stat.match.winner}"/></td>				
				<td><c:out value="${stat.numTurnsPlayer}"/></td>	
                <td><c:out value="${stat.numDiceRolls}"/></td>	
			</tr>
		</c:forEach>
	</table>
</body>
</html>