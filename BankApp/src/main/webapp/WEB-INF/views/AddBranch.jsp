<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Add Branch</title>
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
            <h2 class="form-title">Add New Branch</h2>
            <div class="header-actions">
                <a href="<c:url value='/adminControl?command=DASHBOARD'/>" class="btn btn-back">Dashboard</a>
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
        <form action="<c:url value='/adminControl?command=ADD-BRANCH'/>" method="post">
            
            <label for="name">Branch Name</label>
            <input type="text" id="name" name="name" required class="form-control" placeholder="Branch Name">
            
            <label for="ifsc">IFSC Code</label>
		    <input type="text" id="ifsc" name="ifsc" value="HABA0" pattern="^HABA0\d{6}$" title="Enter valid IFSC (HABA0 + 6 digits)" maxlength="11" required class="form-control">
            
            <label for="address">Branch Address</label>
            <input type="text" id="address" name="address" required class="form-control" placeholder="Branch Address">
           
            <!-- Buttons -->
            <div class="button-group">
                <button type="submit" class="btn">Submit</button>
                <button type="reset" class="btn btn-secondary">Reset</button>
            </div>
        </form>
    </div>
</body>
</html>