<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"  href="/webjars/bootstrap/css/bootstrap.min.css" />
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>

<title>Members</title>
</head>
<body>
	<h2>Members:</h2>
	<div class="container">
		<br />
		<c:if test="${message != null}">
		<div class="alert alert-${messageType}">
			<c:out value="${message}"></c:out>
			<a href="#" class="close" data-dismiss="alert" aria-label="close">X</a>
		</div>
		</c:if>
	</div>
	<a href="/members/create"><span class="glyphicon glyphicon-plus sucess" aria-hidden="true"></span>Create member</a>
	<table class="table table-striped">
		<tr>
			<th>Name</th>
			<th>Boards</th>			
			<th>Actions</th>
		</tr>
		 <c:forEach items="${members}" var="p">
			<tr>
				<td><c:out value="${p.name}"/></td>				
				<td>Member of:
					<ul>
					<c:forEach items="${p.boards}" var="o">
						<li><c:out value="${o.description}"/></li>
					</c:forEach>
					</ul>
				</td>
				<td>
					<a href="/members/edit/${p.id}"><span class="glyphicon glyphicon-pencil warning" aria-hidden="true"></span></a>&nbsp;<a href="/members/delete/${p.id}"><span class="glyphicon glyphicon-trash alert" aria-hidden="true"></a> </td>
			</tr>
		</c:forEach>		
	</table>
</body>
</html>