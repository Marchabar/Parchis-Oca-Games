<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ocaParchis" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"  href="/webjars/bootstrap/css/bootstrap.min.css" />
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>

<ocaParchis:layout pageName="home">
	<title>Achievements</title>
</head>
<body style="background-color:#ececec">
	<h2 style="font-family:monospace">Achievements:</h2>
	<div class="container">
		<br />
		<c:if test="${message != null}">
		<div class="alert alert-${messageType}">
			<c:out value="${message}"></c:out>
			<a href="#" class="close" data-dismiss="alert" aria-label="close"> </a>
		</div>
		</c:if>
	</div>
    <c:forEach items="${achievements}" var="achievement">
        <c:forEach items="${myAchievements}" var="myachievement"> 
            <c:if test="${achievement==myachievement}">
                <div style="margin: 30px;">
                    <div style="display: inline-block; width: 100%;" >
                        <div style="float:left">
                            <spring:url value="/resources/images/admin.png" htmlEscape="true" var="adminImage"/>
                            <img src="${adminImage}" width="150" height="150" style="border: 3px solid #d9534f"/>
                        </div>
                        <div style="text-align: center; border: 3px solid #d9534f; height: 150px;">
                            <h2><strong><c:out value="${achievement.name}"/></strong></h2>
                            <h3><c:out value="${achievement.description}"/></h3>
                        </div>
                    </div>	
                </div>   
            </c:if>
        </c:forEach>
    </c:forEach>
</body>
</ocaParchis:layout>

</html>