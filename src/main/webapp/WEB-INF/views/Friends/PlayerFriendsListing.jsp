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
<title>${profUser.login}'s friend list</title>
</head>
<body style="background-color:#ececec">
	<h2 style="font-family:monospace">${profUser.login}'s friend list:</h2>
	<table class="table table-striped">
		<tr>
            <th>Friend name</th>
            <th>Date Accepted</th>
           	<th>Status</th>
        </tr>
		<c:forEach items="${friends}" var="friend"  varStatus="status">
			<tr>
				<c:if test="${friend.accept==true}">
					<c:if test="${profUser.equals(friend.user1)}">		
					<td><c:out value="${friend.user2.login}"/></td>
					</c:if>
					<c:if test="${profUser.equals(friend.user2)}">		
					<td><c:out value="${friend.user1.login}"/></td>
					</c:if>
				</c:if>

				<td><c:out value="${friend.dateF}"/></td>	
				<c:if test="${friend.accept==true}">
					<c:if test="${profUser.equals(friend.user1)}">
						<c:if test="${friend.user2.userStatus.id == 1}">
							<td style="color: #4a9721"><c:out value="${friend.user2.userStatus}"/></td>
						</c:if>
						<c:if test="${friend.user2.userStatus.id == 2}">
							<td style="color: #6f6f6f"><c:out value="${friend.user2.userStatus}"/></td>
						</c:if>
					</c:if>
					<c:if test="${profUser.equals(friend.user2)}">
						<c:if test="${friend.user1.userStatus.id == 1}">
							<td style="color: #4a9721"><c:out value="${friend.user1.userStatus}"/></td>
						</c:if>
						<c:if test="${friend.user1.userStatus.id == 2}">
							<td style="color: #6f6f6f"><c:out value="${friend.user1.userStatus}"/></td>
						</c:if>
					</c:if>
				</c:if>
			</tr>
		</c:forEach>
	</table>
</body>
</ocaParchis:layout>
</html>