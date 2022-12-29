<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ocaParchis" tagdir="/WEB-INF/tags" %>



<ocaParchis:layout pageName="error">
    <body style= background-color:#ececec>
        <h1 style=text-align:center;margin-top:150px> OUPS... An <span style=color:#d9534f>  ERROR <c:out value="${statusCode}"></c:out> </span> has ocurred :(</h1>
        
        <div style=text-align:center;>
            <img src=/resources/images/errorHandling.png>
        </div>
    </body>

</ocaParchis:layout>
</html>