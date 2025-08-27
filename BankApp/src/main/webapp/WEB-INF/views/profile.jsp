<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
	<title>Profile</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/profile.css">
	<script src="${pageContext.request.contextPath}/static/js/AddCustomer.js"></script>
	<link rel="icon" href="<c:url value='/static/images/favicon.jpg'/>" type="image/x-icon">
</head>
<body>
	<div class="page-header">
    <img src="<c:url value='/static/images/logo.png'/>" alt="Logo" class="logo">
    <h2>Passbook</h2>
    <a href="<c:url value='/customerControl?command=DASHBOARD'/>" class="btn-back">Back to Dashboard</a>
	</div>
	
	<div class="profile-container">
    <!-- Profile Header -->
    <div class="profile-header">
        <div class="profile-info">
            <h2>${customer.firstName} ${customer.lastName}</h2>
            <p><strong>Customer ID:</strong> ${customer.customerId}</p>
            <p><strong>Email:</strong> ${customer.email}</p>
            <p><strong>Contact:</strong> ${customer.contactNo}</p>
            <p><strong>Aadhar:</strong> ${customer.aadhar}</p>
            <p><strong>PAN:</strong> ${customer.pan}</p>
            <p><strong>DOB:</strong> ${customer.dob}</p>
            <p><strong>Gender:</strong> ${customer.gender}</p>
            <p><strong>Customer Since:</strong> ${customer.createdAt}</p>
        </div>
    </div>

    <!-- Accounts Section -->
    <div class="accounts-section">
        <h3>Accounts</h3>
        <table class="styled-table">
            <thead>
                <tr>
                    <th>Account Number</th>
                    <th>Branch</th>
                    <th>Balance</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="acc" items="${accounts}">
                    <tr>
                        <td>${acc.accNo}</td>
                        <td>${acc.ifscCode}</td>
                        <td>&#8377; ${acc.balance}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
	
</body>
</html>