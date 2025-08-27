<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/login.css">
<link rel="icon" href="<c:url value='/static/images/favicon.jpg'/>" type="image/x-icon">
<script src="${pageContext.request.contextPath}/static/js/login.js"></script>
</head>
<body>
	<div class="login-container">
		<h2>Login</h2>

		<!-- JSTL messages -->
		<c:if test="${not empty param.error}">
			<p class="error">${param.error}</p>
		</c:if>
		<c:if test="${not empty param.message}">
			<p class="error">${param.message}</p>
		</c:if>

		<form action="${pageContext.request.contextPath}/login"
			method="post">
			<label for="role">Login As:</label>
			<div class="toggle-container">
	            <input type="radio" id="customer" name="role" value="customer" checked>
	            <label for="customer" class="toggle-option">Customer</label>
	
	            <input type="radio" id="admin" name="role" value="admin">
	            <label for="admin" class="toggle-option">Admin</label>

            	<div class="toggle-slider"></div>
        	</div>
			<input type="email" name="email" placeholder="Enter Email" class="form-control" required> 
			<input type="password" name="password" placeholder="Enter Password" class="form-control" required>
			<button type="submit" class="btn">Login</button>
		</form>
	</div>
</body>
</html>