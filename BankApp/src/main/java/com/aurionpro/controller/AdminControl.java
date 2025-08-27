package com.aurionpro.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.sql.DataSource;

import com.aurionpro.Dao.AccountDao;
import com.aurionpro.Dao.AdminDao;
import com.aurionpro.Dao.BranchDao;
import com.aurionpro.Dao.CustomerDao;
import com.aurionpro.Dao.TransactionDao;
import com.aurionpro.model.Account;
import com.aurionpro.model.BankBranch;
import com.aurionpro.model.Customer;
import com.aurionpro.model.Transaction;
import com.aurionpro.service.AdminService;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/adminControl")
public class AdminControl extends HttpServlet{
	private AdminService adminService;

	@Resource(name = "jdbc/db-source")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		super.init();

		try {
			AdminDao adminDao = new AdminDao(dataSource);
			CustomerDao customerDao = new CustomerDao(dataSource);
			BranchDao branchDao = new BranchDao(dataSource);
			AccountDao accountDao = new AccountDao(dataSource);
			TransactionDao transactionDao = new TransactionDao(dataSource);
			adminService = new AdminService(adminDao, customerDao, branchDao,accountDao, transactionDao);
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			// User is not logged in, redirect to login page
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/login.jsp");
			dispatcher.forward(request, response);
		}
		
		try {
			String theCommand = request.getParameter("command");
			if (theCommand == null || theCommand.isBlank()) {
				theCommand = "DASHBOARD";
			}

			switch (theCommand) {
			case "DASHBOARD":
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/AdminDashboard.jsp");
				dispatcher.forward(request, response);
				break;
				
			case "ADD-CUSTOMER-FORM":
				RequestDispatcher acDispatcher = request.getRequestDispatcher("WEB-INF/views/AddCustomer.jsp");
				acDispatcher.forward(request, response);
				break;
				
			case "ADD-BRANCH-FORM":
				RequestDispatcher abDispatcher = request.getRequestDispatcher("WEB-INF/views/AddBranch.jsp");
				abDispatcher.forward(request, response);
				break;
				
			case "ADD-ACCOUNT-FORM":
				List<Customer> customers = adminService.getAllCustomers();
				List<BankBranch> branches = adminService.getAllBranches();
				request.setAttribute("customers", customers);
				request.setAttribute("branches", branches);
				RequestDispatcher aaDispatcher = request.getRequestDispatcher("WEB-INF/views/AddAccount.jsp");
				aaDispatcher.forward(request, response);
				break;
				
			case "VIEW-CUSTOMERS":
				List<Customer>customerList = adminService.getAllCustomersWithAccounts();
				request.setAttribute("customers", customerList);
				RequestDispatcher vcDispatcher = request.getRequestDispatcher("WEB-INF/views/ViewCustomers.jsp");
				vcDispatcher.forward(request, response);
				break;
				
			case "TRANSACTIONS":
				String type = request.getParameter("type");
				String accNo = request.getParameter("accNo");
				String upi = request.getParameter("upi");
				
				Date fromDate = null;
				String fromDateStr = request.getParameter("fromDate");
				if (fromDateStr != null && !fromDateStr.isEmpty()) {
				    fromDate = Date.valueOf(fromDateStr); // yyyy-MM-dd
				}

				Date toDate = null;
				String toDateStr = request.getParameter("toDate");
				if (toDateStr != null && !toDateStr.isEmpty()) {
				    toDate = Date.valueOf(toDateStr);
				}

				Double minAmount = null;
				String minStr = request.getParameter("minAmount");
				if (minStr != null && !minStr.isEmpty()) {
				    try {
				        minAmount = Double.valueOf(minStr);
				    } catch (NumberFormatException e) {
				        minAmount = null;
				    }
				}

				Double maxAmount = null;
				String maxStr = request.getParameter("maxAmount");
				if (maxStr != null && !maxStr.isEmpty()) {
				    try {
				        maxAmount = Double.valueOf(maxStr);
				    } catch (NumberFormatException e) {
				        maxAmount = null;
				    }
				}
	            List<Transaction> transactions = adminService.getTransactions(type,accNo,fromDate,toDate, minAmount, maxAmount, upi);
	            request.setAttribute("transactions", transactions);
	            switch (type){
	            case "deposit":
	            	RequestDispatcher dpDispatcher = request.getRequestDispatcher("WEB-INF/views/AdminDeposits.jsp");
					dpDispatcher.forward(request, response);
					break;
	            case "withdrawal":
	            	RequestDispatcher wdDispatcher = request.getRequestDispatcher("WEB-INF/views/AdminWithdrawls.jsp");
					wdDispatcher.forward(request, response);
					break;
	            case "transfer":
	            	RequestDispatcher tfDispatcher = request.getRequestDispatcher("WEB-INF/views/AdminTransfers.jsp");
					tfDispatcher.forward(request, response);
					break;
	            case "upi":
	            	RequestDispatcher txnDispatcher = request.getRequestDispatcher("WEB-INF/views/AdminUpi.jsp");
					txnDispatcher.forward(request, response);
					break;
	            }
				break;

			default:
				RequestDispatcher dispatcher2 = request.getRequestDispatcher("WEB-INF/views/AdminDashboard.jsp");
				dispatcher2.forward(request, response);
			}
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			// User is not logged in, redirect to login page
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/login.jsp");
			dispatcher.forward(request, response);
		}
		
		try {
			String theCommand = request.getParameter("command");
			if (theCommand == null || theCommand.isBlank()) {
				theCommand = "DASHBOARD";
			}

			switch (theCommand) {
			case "DASHBOARD":
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/AdminDashboard.jsp");
				dispatcher.forward(request, response);
				break;
				
			case "ADD-CUSTOMER":
				try {
		            // Collect form data
		            String email = request.getParameter("email");
		            String firstName = request.getParameter("firstName");
		            String lastName = request.getParameter("lastName");
		            String gender = request.getParameter("gender");
		            String dob = request.getParameter("dob"); // yyyy-MM-dd
		            String aadhar = request.getParameter("aadhar");
		            String pan = request.getParameter("pan");
		            String contact = request.getParameter("contactNumber");

		            // Build Customer object
		            Customer customer = new Customer();
		            customer.setEmail(email);
		            customer.setFirstName(firstName);
		            customer.setLastName(lastName);
		            customer.setGender(gender);
		            customer.setDob(java.sql.Date.valueOf(dob));
		            customer.setAadhar(aadhar);
		            customer.setPan(pan);
		            customer.setContactNo(contact);

		            // Insert into DB
		            boolean isInserted = adminService.addCustomer(customer);

		            if (isInserted) {
		            	RequestDispatcher acsDispatcher = request.getRequestDispatcher("WEB-INF/views/AddCustomer.jsp?success=Customer added successfully");
		    			acsDispatcher.forward(request, response);
		            } else {
		            	RequestDispatcher acfDispatcher = request.getRequestDispatcher("WEB-INF/views/AddCustomer.jsp?error=Failed to add customer");
		    			acfDispatcher.forward(request, response);
		            }

		        } catch (Exception e) {
		            e.printStackTrace();
		            RequestDispatcher acfeDispatcher = request.getRequestDispatcher("WEB-INF/views/AddCustomer.jsp?error=Something went wrong");
					acfeDispatcher.forward(request, response);
		        }
				break;
				
			case "ADD-BRANCH":
				try {
		            // Collect form data
					String ifsc = request.getParameter("ifsc");
					String branchName = request.getParameter("name");
					String address = request.getParameter("address");

					// Build Branch object
					BankBranch branch = new BankBranch();
					branch.setIfscCode(ifsc);
					branch.setBranchName(branchName);
					branch.setAddress(address);
					branch.setDeleted(false);

		            // Insert into DB
		            boolean isInserted = adminService.addBranch(branch);

		            if (isInserted) {
		            	RequestDispatcher acsDispatcher = request.getRequestDispatcher("WEB-INF/views/AddBranch.jsp?success=Branch added successfully");
		    			acsDispatcher.forward(request, response);
		            } else {
		            	RequestDispatcher acfDispatcher = request.getRequestDispatcher("WEB-INF/views/AddBranch.jsp?error=Failed to add branch");
		    			acfDispatcher.forward(request, response);
		            }

		        } catch (Exception e) {
		            e.printStackTrace();
		            RequestDispatcher acfeDispatcher = request.getRequestDispatcher("WEB-INF/views/AddBranch.jsp?error=Something went wrong");
					acfeDispatcher.forward(request, response);
		        }
				break;
				
			case "ADD-ACCOUNT":
				try {
		            // Collect form data
					String ifsc = request.getParameter("ifscCode");
					String customerId = request.getParameter("customerId");
					String balance = request.getParameter("balance");
					boolean upiEnabled = request.getParameter("upiEnabled") != null;

					// Build Branch object
					Account account = new Account();
					account.setIfscCode(ifsc);
					account.setCustomerId(Integer.valueOf(customerId));
					account.setBalance(Integer.valueOf(balance));
					
					if(adminService.isFirstAccount(Integer.valueOf(customerId))) {
						account.setUpiEnabled(true);
					}
					else {
						if(upiEnabled) {
							adminService.disableUpiAccounts(Integer.valueOf(customerId));
							account.setUpiEnabled(true);
						}
						else {
							account.setUpiEnabled(false);
						}
					}
					
					account.setAccNo(adminService.genrateAccountNumber());

		            // Insert into DB
		            boolean isInserted = adminService.addAccount(account);

		            if (isInserted) {
		            	RequestDispatcher acsDispatcher = request.getRequestDispatcher("WEB-INF/views/AddAccount.jsp?success=Account added successfully");
		    			acsDispatcher.forward(request, response);
		            } else {
		            	RequestDispatcher acfDispatcher = request.getRequestDispatcher("WEB-INF/views/AddAccount.jsp?error=Failed to add Account");
		    			acfDispatcher.forward(request, response);
		            }

		        } catch (Exception e) {
		            e.printStackTrace();
		            RequestDispatcher acfeDispatcher = request.getRequestDispatcher("WEB-INF/views/AddAccount.jsp?error=Something went wrong");
					acfeDispatcher.forward(request, response);
		        }
				break;

			default:
				RequestDispatcher dispatcher2 = request.getRequestDispatcher("WEB-INF/views/AdminDashboard.jsp");
				dispatcher2.forward(request, response);
			}
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
}
