<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.aurionpro.model.Admin"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
	<title>Dashboard</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/AdminDashboard.css">
	<link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" rel="stylesheet" />
	<link rel="icon" href="<c:url value='/static/images/favicon.jpg'/>" type="image/x-icon">
</head>
<body>
	<c:choose>
		<c:when test="${empty sessionScope.customer}">
			<c:redirect url="login.jsp?error=Please login first" />
		</c:when>
		<c:otherwise>
			<div class="dashboard-container">
				<header>
					<div class="logo">
						<img src="<c:url value='/static/images/logo.png'/>" alt="Bank Logo">
					</div>
					<div class="header-right">
						<h2>Welcome, ${sessionScope.customer.firstName} ${sessionScope.customer.lastName}</h2>
						<a href="${pageContext.request.contextPath}/logout" class="logout-btn">Logout</a>
					</div>
				</header>

				<!-- Tile Layout -->
				<div class="tiles">
					<a href="<c:url value='/customerControl?command=PROFILE'/>" class="tile">
						<span class="material-symbols-outlined">account_circle</span>&nbsp View Profile</a>
					<a href="<c:url value='/customerControl?command=ADD-BENEFICIARY-FORM'/>" class="tile">
						<span class="material-symbols-outlined">person_add</span>&nbsp Add Benificiary</a>
					<a href="<c:url value='/customerControl?command=SHOW-PASSBOOK'/>" class="tile">
						<span class="material-symbols-outlined">contract</span>&nbsp View Passbook</a>
					<a href="<c:url value='/customerControl?command=CHANGE-PASSWORD-FORM'/>" class="tile">
						<span class="material-symbols-outlined">lock_reset</span>&nbsp Change Password</a>
					<a href="<c:url value='/customerControl?command=DEPOSIT-FORM'/>" class="tile">
						<span class="material-symbols-outlined">payment_arrow_down</span>&nbsp Deposit</a>
					<a href="<c:url value='/customerControl?command=WITHDRAW-FORM'/>" class="tile">
						<span class="material-symbols-outlined">payments</span>&nbsp Withdraw</a>
					<a href="<c:url value='/customerControl?command=TRANSFER-FORM'/>" class="tile">
						<span class="material-symbols-outlined">communication</span>&nbsp Account Transfer</a>
					<a href="<c:url value='/customerControl?command=UPI-TRANSFER-FORM'/>" class="tile">
						<span class="material-symbols-outlined">upi_pay</span>&nbsp UPI Transaction</a>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</body>
</html>