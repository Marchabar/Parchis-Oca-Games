<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ocaParchis" tagdir="/WEB-INF/tags" %>



<ocaParchis:layout pageName="error">
    <body style= background-color:#ececec>
        <h1 style=text-align:center;margin-top:150px> There has seem to be an <span style=color:#d9534f> ERROR <c:out value="${statusCode}"></c:out> </span></h1>
        <c:if test="${statusCode==404}">
            <h3 style=text-align:center> No page found.</h3>
        </c:if>
        <c:if test="${statusCode==403}">
            <h3 style=text-align:center> Forbidden access.</h3>
        </c:if>
        <c:if test="${statusCode==401}">
            <h3 style=text-align:center> Unauthorized.</h3>
        </c:if>
        <c:if test="${statusCode==401}">
            <h3 style=text-align:center> Bad request.</h3>
        </c:if>
        <c:if test="${statusCode>=500}">
            <h3 style=text-align:center> Server error. Please contact an administrator.</h3>
        </c:if>
        <div style=text-align:center;>
            <img src=/resources/images/errorHandling.png>
        </div>
    </body>

</ocaParchis:layout>
</html>