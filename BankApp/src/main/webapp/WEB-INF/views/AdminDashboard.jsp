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
		<c:when test="${empty sessionScope.admin}">
			<c:redirect url="login.jsp?error=Please login first" />
		</c:when>
		<c:otherwise>
			<div class="dashboard-container">
				<header>
					<div class="logo">
						<img src="<c:url value='/static/images/logo.png'/>" alt="Bank Logo">
					</div>
					<div class="header-right">
						<h2>Welcome, ${sessionScope.admin.firstName} ${sessionScope.admin.lastName}</h2>
						<a href="${pageContext.request.contextPath}/logout" class="logout-btn">Logout</a>
					</div>
				</header>

				<!-- Tile Layout -->
				<div class="tiles">
					<a href="<c:url value='/adminControl?command=ADD-CUSTOMER-FORM'/>" class="tile">
						<span class="material-symbols-outlined">person_add</span>&nbsp Add New Customer</a>
					<a href="<c:url value='/adminControl?command=ADD-ACCOUNT-FORM'/>" class="tile">
						<span class="material-symbols-outlined">account_balance_wallet</span>&nbsp Add Bank Account</a>
					<a href="<c:url value='/adminControl?command=ADD-BRANCH-FORM'/>" class="tile">
						<span class="material-symbols-outlined">add_home_work</span>&nbsp Add Bank Branch</a>
					<a href="<c:url value='/adminControl?command=VIEW-CUSTOMERS'/>" class="tile">
						<span class="material-symbols-outlined">groups</span>&nbsp View All Customers</a>
					<a href="<c:url value='/adminControl?command=TRANSACTIONS&type=deposit'/>" class="tile">
						<span class="material-symbols-outlined">payment_arrow_down</span>&nbsp View Deposits</a>
					<a href="<c:url value='/adminControl?command=TRANSACTIONS&type=withdrawal'/>" class="tile">
						<span class="material-symbols-outlined">payments</span>&nbsp View Withdrawls</a>
					<a href="<c:url value='/adminControl?command=TRANSACTIONS&type=transfer'/>" class="tile">
						<span class="material-symbols-outlined">communication</span>&nbsp View Account Transfers</a>
					<a href="<c:url value='/adminControl?command=TRANSACTIONS&type=upi'/>" class="tile">
						<span class="material-symbols-outlined">upi_pay</span>&nbsp View UPI Transactions</a>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</body>
</html>