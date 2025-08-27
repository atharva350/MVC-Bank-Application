<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Add Customer</title>
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

        <!-- ===== Form ===== -->
        <form action="<c:url value='/adminControl?command=ADD-CUSTOMER'/>" method="post">
            
            <label for="email">Email</label>
            <input type="email" id="email" name="email" required class="form-control" placeholder="Email Address">
            
            <div class="form-row">
		    	<div class="form-group">
		            <label for="firstName">First Name</label>
		            <input type="text" id="firstName" name="firstName" required class="form-control" placeholder="First Name">
		        </div>
		        <div class="form-group">
		            <label for="lastName">Last Name</label>
		            <input type="text" id="lastName" name="lastName" required class="form-control" placeholder="Last Name">
            	</div>
            </div>
            <label for="contact">Contact Number</label>
		    <input type="text" id="contactNumber" name="contactNumber" pattern="\d{10}" maxlength="10" required class="form-control" placeholder="Contact Number">
            <div class="form-row">
		    	<div class="form-group">
			        <label>Gender</label>
			        <div class="radio-group">
			            <label><input type="radio" name="gender" value="M" required> Male</label>
			            <label><input type="radio" name="gender" value="F"> Female</label>
			            <label><input type="radio" name="gender" value="O"> Other</label>
			        </div>
		    	</div>
	
			    <div class="form-group">
			        <label for="dob">Date of Birth</label>
			        <input type="date" id="dob" name="dob" required class="form-control">
			    </div>
			</div>
            <div class="form-row">
		    	<div class="form-group">
		            <label for="aadhar">Aadhar Number</label>
		            <input type="text" id="aadhar" name="aadhar"  pattern="\d{12}" maxlength="12" required class="form-control" placeholder="Aadhar Number">
            	</div>
            	<div class="form-group">
            		<label for="pan">PAN Number</label>
            		<input type="text" id="pan" name="pan" pattern="[A-Z]{5}[0-9]{4}[A-Z]{1}" maxlength="10" required class="form-control" placeholder="Pan Number (AAAAA1111A)">
				</div>
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