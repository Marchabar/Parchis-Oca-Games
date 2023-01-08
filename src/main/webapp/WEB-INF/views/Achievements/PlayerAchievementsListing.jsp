<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ocaParchis" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"  href="/webjars/bootstrap/css/bootstrap.min.css" />
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>

<ocaParchis:layout pageName="home">
	<title>${profUser.login}'s achievements</title>
</head>
<body style="background-color:#ececec">
	<h2 style="font-family:monospace">${profUser.login}'s achievements:</h2>
    <div style="display: inline-block; width: 100%;" >
		<div style="float:left">
			<sec:authorize access="hasAuthority('admin')">
                <a href="/achievements/create" class="btn btn-danger"><span class="glyphicon glyphicon glyphicon-plus" style="margin-right:5px"></span>Create Achievement</a>
            </sec:authorize>
		</div>
		<div style="float:right">
			<h1 style="text-align: right;"><span style="color:#d9534f">${fn:length(myAchievements)} </span><span> / ${fn:length(achievements)}</span></h1>
		</div>
	</div>
    
    <c:forEach items="${achievements}" var="achievement">
        <c:choose>
            <c:when test="${fn: contains(myAchievements, achievement)}">
                <div style="margin: 20px;">
                    <div style="display: inline-block; width: 100%; background-color: #e3827f;" >
                        <div style="float:left">
                            <spring:url value="/resources/images/achievementImages/${achievement.fileImage}.png" htmlEscape="true" var="achivementImage"/>
                            <img src="${achivementImage}" width="120" height="120" style="border: 3px solid #d9534f"/>
                        </div>
                        <div style="text-align: center; border: 3px solid #d9534f; height: 120px;">
                            <h2><strong><c:out value="${achievement.name}"/></strong></h2>
                            <h3><c:out value="${achievement.description}"/></h3>
                        </div>
                    </div>	
                </div>   
            </c:when>
            <c:otherwise>
                <div style="margin: 20px;">
                    <div style="display: inline-block; width: 100%; background-color: #cccccc;" >
                        <div style="float:left">
                            <spring:url value="/resources/images/achievementImages/${achievement.fileImage}.png" htmlEscape="true" var="achivementImage"/>
                            <img src="${achivementImage}" width="120" height="120" style="border: 3px solid black"/>
                        </div>
                        <div style="text-align: center; border: 3px solid black; height: 120px;">
                            <h2><strong><c:out value="${achievement.name}"/></strong></h2>
                            <h3><c:out value="${achievement.description}"/></h3>
                        </div>
                    </div>	
                </div>   
            </c:otherwise>
        </c:choose>
    </c:forEach>
</body>
</ocaParchis:layout>

</html>