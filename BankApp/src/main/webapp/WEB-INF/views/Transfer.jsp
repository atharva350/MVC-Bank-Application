<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Transfer</title>
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
			<h2 class="form-title">Transfer to Account</h2>
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

		<form action="<c:url value='/customerControl?command=TRANSFER'/>"
			method="post">

			<!-- Sender Account Selection -->
			<label for="account">Select Your Account</label> <select id="account"
				name="account" class="form-control" required>
				<option value="" disabled selected>-- Select Account --</option>
				<c:forEach var="acc" items="${accounts}">
					<option value="${acc.accNo}">${acc.accNo}</option>
				</c:forEach>
			</select>

			<!-- Beneficiary Selection -->
			<label for="beneficiary">Select Beneficiary</label> <select
				id="beneficiary" name="beneficiaryAccNo" class="form-control"
				required>
				<option value="" disabled selected>-- Select Beneficiary --</option>
				<c:forEach var="b" items="${beneficiaries}">
					<option value="${b.beneficiaryAccNo}">${b.nickname} -
						${b.beneficiaryAccNo}</option>
				</c:forEach>
			</select>

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