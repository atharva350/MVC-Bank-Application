<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Passbook</title>
	<link rel="stylesheet" href="<c:url value='/static/css/passbook.css'/>">
	<script src="${pageContext.request.contextPath}/static/js/passbook.js"></script>
	<link rel="icon" href="<c:url value='/static/images/favicon.jpg'/>" type="image/x-icon">
</head>
<body>
    <div class="page-header">
    <img src="<c:url value='/static/images/logo.png'/>" alt="Logo" class="logo">
    <h2>Passbook</h2>
    <a href="<c:url value='/customerControl?command=DASHBOARD'/>" class="btn-back">Back to Dashboard</a>
	</div>

<!-- Filters -->
<div class="filter-container">
    <form method="get" action='/BankApp/customerControl'>
        <input type="hidden" name="command" value="SHOW-PASSBOOK"/>

        <!-- Account Dropdown -->
        <label for="accNo">Account:</label>
        <select id="accNo" name="accNo">
            <c:forEach var="acc" items="${accounts}">
                <option value="${acc.accNo}" 
                    <c:if test="${acc.accNo == selectedAccNo}">selected</c:if>>
                    ${acc.accNo} - ${acc.branchName}
                </option>
            </c:forEach>
        </select>

        <!-- Date Range -->
        <label for="fromDate">From:</label>
        <input type="date" id="fromDate" name="fromDate" value="${fromDate}"/>

        <label for="toDate">To:</label>
        <input type="date" id="toDate" name="toDate" value="${toDate}"/>

        <!-- Buttons -->
        <button type="submit" class="btn btn-apply">Apply</button>
        <button type="reset" class="btn btn-reset">Reset</button>
    </form>
</div>

<!-- Table -->
<div class="table-container">
    <table class="styled-table">
        <thead>
            <tr>
                <th>Date & Time</th>
                <th>Description</th>
                <th>Debit (&#8377)</th>
                <th>Credit (&#8377)</th>
                <th>Balance (&#8377)</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="txn" items="${transactions}">
            <tr>
                <td>${txn.transactionTime}</td>

                <!-- Description -->
				<td>
				    <c:choose>
				        <c:when test="${txn.transactionType == 'deposit'}">
				            Deposit
				        </c:when>
				        <c:when test="${txn.transactionType == 'withdrawal'}">
				            Withdrawal
				        </c:when>
				        <c:when test="${txn.transactionType == 'transfer'}">
				            <c:choose>
				                <c:when test="${txn.senderAcc == selectedAccNo}">
				                    Transfer to ${txn.receiverAcc}
				                </c:when>
				                <c:otherwise>
				                    Transfer from ${txn.senderAcc}
				                </c:otherwise>
				            </c:choose>
				        </c:when>
				        <c:when test="${txn.transactionType == 'upi'}">
				            <c:choose>
				                <c:when test="${txn.senderAcc == selectedAccNo}">
				                    UPI Payment to ${txn.receiverUpiId}
				                </c:when>
				                <c:otherwise>
				                    UPI Payment from ${txn.senderUpiId}
				                </c:otherwise>
				            </c:choose>
				        </c:when>
				    </c:choose>
				</td>


                <!-- Debit / Credit -->
                <td class="debit">
                    <c:if test="${txn.senderAcc == selectedAccNo}">
                        ${txn.amount}
                    </c:if>
                </td>
                <td class="credit">
                    <c:if test="${txn.receiverAcc == selectedAccNo}">
                        ${txn.amount}
                    </c:if>
                </td>

                <!-- Balance -->
                <td>
                    <c:choose>
                        <c:when test="${txn.senderAcc == selectedAccNo}">
                            ${txn.senderNewBalance}
                        </c:when>
                        <c:when test="${txn.receiverAcc == selectedAccNo}">
                            ${txn.receiverNewBalance}
                        </c:when>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
