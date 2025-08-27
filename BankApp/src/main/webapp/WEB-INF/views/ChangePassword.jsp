<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Change Password</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/AddCustomer.css">
	<script src="${pageContext.request.contextPath}/static/js/AddCustomer.js"></script>
	<link rel="icon" href="<c:url value='/static/images/favicon.jpg'/>" type="image/x-icon">
</head>
<body>
	<div class="form-container">
        
        <!-- ===== Header Row ===== -->
        <div class="form-header">
            <div class="logo">
                <img src="<c:url value='/static/images/logo.png'/>" alt="Bank Logo">
            </div>
            <h2 class="form-title">Change Password</h2>
            <div class="header-actions">
                <a href="<c:url value='/customerControl?command=DASHBOARD'/>" class="btn btn-back">Dashboard</a>
            </div>
        </div>

        <!-- Show error/success messages -->
        <c:if test="${not empty param.error}">
            <p class="error">${param.error}</p>
        </c:if>
        <c:if test="${not empty param.success}">
            <p class="success">${param.success}</p>
        </c:if>

        <!-- ===== Form ===== -->
        <form action="<c:url value='/customerControl?command=CHANGE-PASSWORD'/>" method="post">
            
            <label for="oldPassword">Old Password</label>
            <input type="password" id="oldPassword" name="oldPassword" required class="form-control" placeholder="Old Password">
            
            <label for="newPassword">New Password</label>
            <input type="password" id="newPassword" name="newPassword" required class="form-control" placeholder="New Password">
            
            <label for="confirmPassword">Confirm Password</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required class="form-control" placeholder="Confirm Password">
            
            <!-- Buttons -->
            <div class="button-group">
                <button type="submit" class="btn">Submit</button>
                <button type="reset" class="btn btn-secondary">Reset</button>
            </div>
        </form>
    </div>
</body>
</html>