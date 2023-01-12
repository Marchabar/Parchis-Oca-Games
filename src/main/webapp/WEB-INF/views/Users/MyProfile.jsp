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
	<title>Users</title>
</head> 
<body style="background-color:#ececec">
	<div style="text-align: center;"><img src="/resources/images/user.png" style="margin: 30px;" width="200" height="200"/></div>
	<h1 style="color:#d9534f;text-align: center;font-family:monospace;"><b><c:out value="${user.login}"></c:out></b></h1>
	<h1 style="color:#d9534f;text-align: center;font-family:monospace;"><b><a href="/users/editPassword/${user.id}" style="color:#d9534f;text-align: center;font-family:monospace;">Edit Password</a></h1>
    <div style="display: inline-block; width: 100%;" >
		<div style="float:left;text-align;">
			<h1><img src="/resources/images/stats.png" width="24" height="24"> <a href="/playerstats" style="color:black;text-align: center;font-family:monospace;">My stats</a></h1>
		</div>
		<div style="float:right;">
			<h1><img src="/resources/images/people.png" width="24" height="24"> <a href="/friends/myfriends" style="color:black;text-align: center;font-family:monospace;">My friends</a></h1>
		</div>
	</div>
    <div style="text-align: center;">
        <h1><span class="glyphicon glyphicon-star" aria-hidden="true" style="color:#d9534f"> <a href="/achievements" style="color:black;text-align: center;font-family:monospace;"> My achievements </a> <span class="glyphicon glyphicon-star" aria-hidden="true" style="color:#d9534f"></span></h1>
    </div>
</body>
</ocaParchis:layout>

</html>