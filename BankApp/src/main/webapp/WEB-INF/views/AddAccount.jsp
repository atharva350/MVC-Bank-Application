<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
	<title>Add Account</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/AddCustomer.css">
	<script src="${pageContext.request.contextPath}/static/js/AddCustomer.js"></script>
	<link rel="icon" href="<c:url value='/static/images/favicon.jpg'/>" type="image/x-icon">
</head>
<body>
	<div class="form-container">
    <div class="form-header">
            <div class="logo">
                <img src="<c:url value='/static/images/logo.png'/>" alt="Bank Logo">
            </div>
            <h2 class="form-title">Add New Customer</h2>
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

    <form action="<c:url value='/adminControl?command=ADD-ACCOUNT'/>" method="post">

        <!-- Customer Dropdown -->
        <label for="customer">Select Customer</label>
        <select id="customer" name="customerId" class="form-control" required>
            <option value="" disabled selected>-- Select Customer --</option>
            <c:forEach var="cust" items="${customers}">
                <option value="${cust.customerId}">
                    ${cust.customerId} - ${cust.firstName} ${cust.lastName}
                </option>
            </c:forEach>
        </select>

        <!-- Branch Dropdown -->
        <label for="branch">Select Branch</label>
        <select id="branch" name="ifscCode" class="form-control" required>
            <option value="" disabled selected>-- Select Branch --</option>
            <c:forEach var="branch" items="${branches}">
                <option value="${branch.ifscCode}">
                    ${branch.ifscCode} - ${branch.branchName}
                </option>
            </c:forEach>
        </select>

        <!-- Initial Balance -->
        <label for="balance">Initial Balance</label>
        <input type="number" id="balance" name="balance" class="form-control"
               step="0.01" min="0" placeholder="Enter initial balance" required>

        <!-- UPI Checkbox -->
        <div class="form-group-inline">
        	<label for="upi">Use this account for UPI transactions
            <input type="checkbox" id="upi" name="upiEnabled" value="true">
            </label>
        </div>

        <!-- Buttons -->
        <div class="button-group">
                <button type="submit" class="btn">Submit</button>
                <button type="reset" class="btn btn-secondary">Reset</button>
        </div>
    </form>
</div>
</body>
</html>