<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="mvc"%>
<%@ taglib prefix="ocaParchis" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>

<ocaParchis:layout pageName="home">
	<title>Create Achievement</title>
</head>
<body style="background-color:#ececec">
	<h2 style="font-family:monospace">Create Achievement</h2>
	<mvc:form modelAttribute="achievement">
		<td><mvc:input type="hidden" path="name" readOnly="true" value=""/></td>
		<td><mvc:input type="hidden" path="description" readOnly="true" value=""/></td>
		<td><mvc:input type="hidden" path="fileImage" readOnly="true" value="CustomAchievement"/></td>
        <div class="control-group">
			<ocaParchis:selectField name="achievementType" label="Achievement Type" names="${types}" size="${types.size()}"/>
		</div>
		<ocaParchis:inputField label="Value" name="value"/>
		<a href="/achievements" class="btn btn-secondary" style="color:#d9534f">Cancel</a>
		<input type="submit" value="Save" class="btn btn-danger"/>           
	</mvc:form>
</body>
</ocaParchis:layout>

</html>