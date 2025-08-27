<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>View Deposits</title>
	<link rel="stylesheet" href="<c:url value='/static/css/transactions.css'/>">
	<script src="${pageContext.request.contextPath}/static/js/passbook.js"></script>
	<link rel="icon" href="<c:url value='/static/images/favicon.jpg'/>" type="image/x-icon">
</head>
<body>
	<div class="page-header">
    <img src="<c:url value='/static/images/logo.png'/>" alt="Logo" class="logo">
    <h2>Deposits</h2>
    <a href="<c:url value='/adminControl?command=DASHBOARD'/>" class="btn-back">Dashboard</a>
</div>

<form method="get" action="<c:url value='/adminControl?command=TRANSACTIONS'/>" class="filter-form">
    <input type="hidden" name="command" value="TRANSACTIONS"/>
    <input type="hidden" name="type" value="deposit"/>

    <label>Account No:</label>
    <input type="text" name="accNo" value="${param.accNo}" placeholder="Account No"/>

    <label>From:</label>
    <input type="date" name="fromDate" id="fromDate" value="${param.fromDate}"/>

    <label>To:</label>
    <input type="date" name="toDate" id="toDate" value="${param.toDate}"/>
	<br>
    <label>Min Amount:</label>
    <input type="number" name="minAmount" id="minAmount" step="0.01" value="${param.minAmount}"/>

    <label>Max Amount:</label>
    <input type="number" name="maxAmount" id="maxAmount" step="0.01" value="${param.maxAmount}"/>

    <button type="submit" class="btn">Filter</button>
    <a href="<c:url value='/adminControl?command=TRANSACTIONS&type=deposit'/>" class="btn">Reset</a>
</form>

<div class="table-container">
    <table class="styled-table">
        <thead>
            <tr><th>Date & Time</th><th>Account</th><th>Amount (&#8377)</th></tr>
        </thead>
        <tbody>
            <c:forEach var="txn" items="${transactions}">
                <tr>
                    <td>${txn.transactionTime}</td>
                    <td>${txn.senderAcc}</td>
                    <td>${txn.amount}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>