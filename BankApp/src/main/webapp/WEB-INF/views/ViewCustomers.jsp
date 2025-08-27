<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>View Customers</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/ViewCustomers.css">
	<link rel="icon" href="<c:url value='/static/images/favicon.jpg'/>" type="image/x-icon">
	<script src="${pageContext.request.contextPath}/static/js/ViewCustomers.js"></script>
</head>
<body>
<div class="page-header">
    <img src="<c:url value='/static/images/logo.png'/>" alt="Logo" class="logo">
    <h2>All Customers</h2>
    <a href="<c:url value='/adminControl?command=DASHBOARD'/>" class="btn-back">Back to Dashboard</a>
</div>
<div class="table-toolbar">
    <!-- Search -->
    <input type="text" id="searchInput" class="search-input" 
           placeholder="Search customers by name or ID">
</div>
<div class="table-container">
    <table class="styled-table">
        <thead>
            <tr>
                <th>Customer ID</th>
                <th>Name</th>
                <th>Customer Since</th>
                <th>No. of Accounts</th>
                <th>Account Number</th>
                <th>Branch</th>
                <th>Balance</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="cust" items="${customers}">
            <tr>
                <td>${cust.customerId}</td>
                <td>${cust.firstName} ${cust.lastName}</td>
                <td>${cust.createdAt}</td>
                <td>${cust.accountCount}</td>
                <td>
                    <c:forEach var="acc" items="${cust.accounts}">
                        <div>${acc.accNo}</div>
                    </c:forEach>
                </td>
                <td>
                    <c:forEach var="acc" items="${cust.accounts}">
                        <div>${acc.branchName}</div>
                    </c:forEach>
                </td>
                <td>
                    <c:forEach var="acc" items="${cust.accounts}">
                        <div>&#8377; ${acc.balance}</div>
                    </c:forEach>
                </td>
                <td>
                    <c:forEach var="acc" items="${cust.accounts}">
                        <div class="actions">
                            <a href="<c:url value='/transactions?type=withdrawals&accNo=${acc.accNo}'/>" class="btn-small">Withdrawals</a>
                            <a href="<c:url value='/transactions?type=deposits&accNo=${acc.accNo}'/>" class="btn-small">Deposits</a>
                            <a href="<c:url value='/transactions?type=transfers&accNo=${acc.accNo}'/>" class="btn-small">Transfers</a>
                            <a href="<c:url value='/transactions?type=upi&accNo=${acc.accNo}'/>" class="btn-small">UPI</a>
                        </div>
                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
</html>