<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Add Beneficiary</title>
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
            <h2 class="form-title">Add New Beneficiary</h2>
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
        <form action="<c:url value='/customerControl?command=ADD-BENEFICIARY'/>" method="post">
            
            <label for="name">Beneficiary Nickname</label>
            <input type="text" id="name" name="name" required class="form-control" placeholder="Beneficiary Nickname">
            
            <label for="account">Beneficiary Account Number</label>
		    <input type="number" id="account" name="account" title="Enter valid Account Number" placeholder="Beneficiary Account Number" maxlength="10" required class="form-control">
            
            <!-- Buttons -->
            <div class="button-group">
                <button type="submit" class="btn">Submit</button>
                <button type="reset" class="btn btn-secondary">Reset</button>
            </div>
        </form>
    </div>
</body>
</html>