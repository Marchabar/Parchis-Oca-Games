<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ocaParchis" tagdir="/WEB-INF/tags" %>



<ocaParchis:layout pageName="error">
	<spring:url value="/resources/images/admin.png" var="adminImage"/>
    <img src="${adminImage}"/>

    <h2>Something happened...</h2>

    <p>${exception.message}</p>
</ocaParchis:layout>
</html>