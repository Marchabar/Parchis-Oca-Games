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
	<title>Chat</title>
</head>
<body style="background-color:#ececec">
	<h2 style="font-family:monospace">Chat:</h2>
	<table class="table table-striped">
		<tr>			
			<th>User</th>
			<th>Message</th>
			<th>Time</th>
		</tr>
		<c:forEach items="${messagesChat}" var="messagesChat">
			<tr>		
				<c:if test="${usersInside.contains(messagesChat.user)}">
											<td><span style="color:${messagesChat.user.prefColor.rgb}">
													<c:out value="${messagesChat.user.login}" />
												</span></td>
										</c:if>
										<c:if test="${!usersInside.contains(messagesChat.user)}">
											<td><span style="color:#000000">
													<c:out value="${messagesChat.user.login}" />
												</span></td>
										</c:if>						
				<td><c:out value="${messagesChat.description}"/></td>	
				<td><c:out value="${messagesChat.time}"/></td>				
			</tr>
		</c:forEach>
	</table>
	<div style="display: inline-block; width: 100%;" >
		<div style="float:left">
			<a href="/matches/${matchId}" class="btn btn-danger"><span class="glyphicon glyphicon-chevron-left" style="margin-right:5px"></span>Back To Game</a>
		</div>
		<div style="float:right">
			<a href="/matches/${matchId}/chat/send" class="btn btn-danger">New Message</a>
		</div>
	</div>
	
</body>
</ocaParchis:layout>

</html>