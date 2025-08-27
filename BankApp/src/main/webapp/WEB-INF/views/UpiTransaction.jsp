<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>UPI Transcation</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/css/AddCustomer.css">
<script
	src="${pageContext.request.contextPath}/static/js/AddCustomer.js"></script>
<link rel="icon" href="<c:url value='/static/images/favicon.jpg'/>"
	type="image/x-icon">
</head>
<body>
	<div class="form-container">
		<div class="form-header">
			<div class="logo">
				<img src="<c:url value='/static/images/logo.png'/>" alt="Bank Logo">
			</div>
			<h2 class="form-title">Transfer via UPI</h2>
			<div class="header-actions">
				<a href="<c:url value='/customerControl?command=DASHBOARD'/>"
					class="btn btn-back">Dashboard</a>
			</div>
		</div>

		<!-- Show error/success messages -->
		<c:if test="${not empty param.error}">
			<p class="error">${param.error}</p>
		</c:if>
		<c:if test="${not empty param.success}">
			<p class="success">${param.success}</p>
		</c:if>

		<form action="<c:url value='/customerControl?command=UPI'/>"
			method="post">
			
			<!-- UPI -->
			<label for="upi">Enter UPI ID</label> <input type="email"
				id="upi" name="upi" class="form-control" placeholder="Enter UPI ID" required>

			<!-- Amount -->
			<label for="amount">Enter Amount</label> <input type="number"
				id="amount" name="amount" class="form-control" step="0.01" min="0.01"
				placeholder="Enter Amount" required>

			<!-- Buttons -->
			<div class="button-group">
				<button type="submit" class="btn">Transfer</button>
				<button type="reset" class="btn btn-secondary">Reset</button>
			</div>
		</form>
	</div>
</body>
</html>