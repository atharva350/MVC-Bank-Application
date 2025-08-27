package com.aurionpro.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.sql.DataSource;

import org.mindrot.jbcrypt.BCrypt;

import com.aurionpro.Dao.AccountDao;
import com.aurionpro.Dao.BranchDao;
import com.aurionpro.Dao.CustomerDao;
import com.aurionpro.Dao.TransactionDao;
import com.aurionpro.model.Account;
import com.aurionpro.model.Beneficiary;
import com.aurionpro.model.Customer;
import com.aurionpro.model.Transaction;
import com.aurionpro.service.CustomerService;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/customerControl")
public class CustomerControl extends HttpServlet{
	private CustomerService customerService;

	@Resource(name = "jdbc/db-source")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		super.init();

		try {
			CustomerDao customerDao = new CustomerDao(dataSource);
			BranchDao branchDao = new BranchDao(dataSource);
			AccountDao accountDao = new AccountDao(dataSource);
			TransactionDao transactionDao = new TransactionDao(dataSource);
			customerService = new CustomerService(customerDao, branchDao,accountDao, transactionDao);
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
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/CustomerDashboard.jsp");
				dispatcher.forward(request, response);
				break;
				
			case "ADD-BENEFICIARY-FORM":
				RequestDispatcher abdispatcher = request.getRequestDispatcher("WEB-INF/views/AddBeneficiary.jsp");
				abdispatcher.forward(request, response);
				break;
				
			case "CHANGE-PASSWORD-FORM":
				RequestDispatcher cpdispatcher = request.getRequestDispatcher("WEB-INF/views/ChangePassword.jsp");
				cpdispatcher.forward(request, response);
				break;
				
			case "DEPOSIT-FORM":
				Customer customer = (Customer) session.getAttribute("customer");
				List<Account> accounts = customerService.getCustomerAccounts(customer);
				request.setAttribute("accounts", accounts);
				
				RequestDispatcher dpdispatcher = request.getRequestDispatcher("WEB-INF/views/Deposit.jsp");
				dpdispatcher.forward(request, response);
				break;
				
			case "WITHDRAW-FORM":
				Customer customer2 = (Customer) session.getAttribute("customer");
				List<Account> accounts2 = customerService.getCustomerAccounts(customer2);
				request.setAttribute("accounts", accounts2);
				RequestDispatcher whdispatcher = request.getRequestDispatcher("WEB-INF/views/Withdraw.jsp");
				whdispatcher.forward(request, response);
				break;
				
			case "TRANSFER-FORM":
				Customer customer3 = (Customer) session.getAttribute("customer");
				List<Account> accounts3 = customerService.getCustomerAccounts(customer3);
				List<Beneficiary> beneficiaries = customerService.getCustomerBeneficiaries(customer3);
				request.setAttribute("accounts", accounts3);
				request.setAttribute("beneficiaries",beneficiaries);
				RequestDispatcher tfdispatcher = request.getRequestDispatcher("WEB-INF/views/Transfer.jsp");
				tfdispatcher.forward(request, response);
				break;
				
			case "UPI-TRANSFER-FORM":
				RequestDispatcher updispatcher = request.getRequestDispatcher("WEB-INF/views/UpiTransaction.jsp");
				updispatcher.forward(request, response);
				break;
				
			case "PROFILE":
				Customer customer5 = (Customer) session.getAttribute("customer");
				List<Account> accounts5 = customerService.getCustomerAccounts(customer5);
				request.setAttribute("accounts", accounts5);
				request.setAttribute("customer", customer5);
				RequestDispatcher ppdispatcher = request.getRequestDispatcher("WEB-INF/views/profile.jsp");
				ppdispatcher.forward(request, response);
				break;
				
			case "SHOW-PASSBOOK":
				Customer customer4 = (Customer) session.getAttribute("customer");
				List<Account> accounts4 = customerService.getCustomerAccounts(customer4);
				// Read parameter
				String accNoStr = request.getParameter("accNo");
				long accNo;

				// If accNo is null → pick first account of customer
				if (accNoStr == null || accNoStr.isEmpty()) {
				    if (accounts4 != null && !accounts4.isEmpty()) {
				        accNo = accounts4.get(0).getAccNo(); // default to first account
				    } else {
				        throw new ServletException("No accounts found for this customer.");
				    }
				} else {
				    accNo = Long.parseLong(accNoStr);
				}
				
				String fromDateStr = request.getParameter("fromDate");
			    String toDateStr = request.getParameter("toDate");

			    Date fromDate = null, toDate = null;
			    if (fromDateStr != null && !fromDateStr.isEmpty() &&
			        toDateStr != null && !toDateStr.isEmpty()) {
			        fromDate = Date.valueOf(fromDateStr);
			        toDate = Date.valueOf(toDateStr);
			    }

				// Now use accNo for passbook
				List<Transaction> transactions = customerService.getPassbookTransactions(accNo, fromDate, toDate);
				request.setAttribute("transactions", transactions);
				request.setAttribute("selectedAccNo", accNo);
				request.setAttribute("accounts", accounts4);

			    RequestDispatcher pbdispatcher = request.getRequestDispatcher("/WEB-INF/views/PassBook.jsp");
			    pbdispatcher.forward(request, response);
			    break;
				
			default:
				RequestDispatcher dispatcher2 = request.getRequestDispatcher("WEB-INF/views/CustomerDashboard.jsp");
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
				
			case "ADD-BENEFICIARY":
				try {
		            // Collect form data
		            String name = request.getParameter("name");
		            String account = request.getParameter("account");

		            // Build Customer object
		            Beneficiary beneficiary = new Beneficiary();
		            beneficiary.setNickname(name);
		            beneficiary.setBeneficiaryAccNo(Long.valueOf(account));

		            // Insert into DB
		            boolean isInserted = customerService.addBeneficiary(beneficiary,(Customer) session.getAttribute("customer"));

		            if (isInserted) {
		            	RequestDispatcher acsDispatcher = request.getRequestDispatcher("WEB-INF/views/AddBeneficiary.jsp?success=Beneficiary added successfully");
		    			acsDispatcher.forward(request, response);
		            } else {
		            	RequestDispatcher acfDispatcher = request.getRequestDispatcher("WEB-INF/views/AddBeneficiary.jsp?error=Failed to add Beneficiary");
		    			acfDispatcher.forward(request, response);
		            }

		        } catch (Exception e) {
		            e.printStackTrace();
		            RequestDispatcher acfeDispatcher = request.getRequestDispatcher("WEB-INF/views/AddBeneficiary.jsp?error=Something went wrong");
					acfeDispatcher.forward(request, response);
		        }
				break;
				
			case "CHANGE-PASSWORD":
				try {
		            // Collect form data
		            String oldPassword = request.getParameter("oldPassword");
		            String newPassword = request.getParameter("newPassword");
		            String confirmPassword = request.getParameter("confirmPassword");
		            
		            Customer customer = (Customer) session.getAttribute("customer");
		            
		            if(!newPassword.equals(confirmPassword)) {
		            	RequestDispatcher npcpDispatcher = request.getRequestDispatcher("WEB-INF/views/ChangePassword.jsp?error=Password and Confirm Password didn't match");
		    			npcpDispatcher.forward(request, response);
		            }
		            
		            if(BCrypt.checkpw(oldPassword,customer.getPassword())) {
		            	
			            // Insert into DB
			            boolean isInserted = customerService.changePassword(newPassword,customer);
	
			            if (isInserted) {
			            	RequestDispatcher acsDispatcher = request.getRequestDispatcher("WEB-INF/views/ChangePassword.jsp?success=Password Changed successfully");
			    			acsDispatcher.forward(request, response);
			            } else {
			            	RequestDispatcher acfDispatcher = request.getRequestDispatcher("WEB-INF/views/ChangePassword.jsp?error=Failed to change Password");
			    			acfDispatcher.forward(request, response);
			            }
		            }
		            else {
		            	RequestDispatcher acfDispatcher = request.getRequestDispatcher("WEB-INF/views/ChangePassword.jsp?error=old Password didn't match");
		    			acfDispatcher.forward(request, response);
		            }

		        } catch (Exception e) {
		            e.printStackTrace();
		            RequestDispatcher acfeDispatcher = request.getRequestDispatcher("WEB-INF/views/AddBeneficiary.jsp?error=Something went wrong");
					acfeDispatcher.forward(request, response);
		        }
				break;
				
			case "DEPOSIT":
				try {
		            // Collect form data
		            long accNo = Long.valueOf(request.getParameter("account"));
		            double amount = Double.valueOf(request.getParameter("amount"));

		            // Insert into DB
		            double newBalance = customerService.deposit(accNo,amount);

		            if (newBalance>-1) {
		            	RequestDispatcher acsDispatcher = request.getRequestDispatcher("WEB-INF/views/Deposit.jsp?success=Deposit Successfull. New Balance is "+newBalance);
		    			acsDispatcher.forward(request, response);
		            } else {
		            	RequestDispatcher acfDispatcher = request.getRequestDispatcher("WEB-INF/views/Deposit.jsp?error=Failed to Deposit");
		    			acfDispatcher.forward(request, response);
		            }

		        } catch (Exception e) {
		            e.printStackTrace();
		            RequestDispatcher acfeDispatcher = request.getRequestDispatcher("WEB-INF/views/Deposit.jsp?error=Something went wrong");
					acfeDispatcher.forward(request, response);
		        }
				break;
				
			case "WITHDRAW":
				try {
		            // Collect form data
		            long accNo = Long.valueOf(request.getParameter("account"));
		            double amount = Double.valueOf(request.getParameter("amount"));

		            // Insert into DB
		            double newBalance = customerService.withdraw(accNo,amount);

		            if (newBalance>-1) {
		            	RequestDispatcher acsDispatcher = request.getRequestDispatcher("WEB-INF/views/Withdraw.jsp?success=Withdraw Successfull. New Balance is "+newBalance);
		    			acsDispatcher.forward(request, response);
		            }else if(newBalance == -10) {
		            	RequestDispatcher acfDispatcher = request.getRequestDispatcher("WEB-INF/views/Withdraw.jsp?error=Insufficient Balance");
		    			acfDispatcher.forward(request, response);
		            }
		            else {
		            	RequestDispatcher acfDispatcher = request.getRequestDispatcher("WEB-INF/views/Withdraw.jsp?error=Failed to Withdraw");
		    			acfDispatcher.forward(request, response);
		            }

		        } catch (Exception e) {
		            e.printStackTrace();
		            RequestDispatcher acfeDispatcher = request.getRequestDispatcher("WEB-INF/views/Withdraw.jsp?error=Something went wrong");
					acfeDispatcher.forward(request, response);
		        }
				break;
				
			case "TRANSFER":
				try {
		            // Collect form data
		            long senderAccNo = Long.valueOf(request.getParameter("account"));
		            double amount = Double.valueOf(request.getParameter("amount"));
		            long recieverAccNo = Long.valueOf(request.getParameter("beneficiaryAccNo"));

		            // Insert into DB
		            double newBalance = customerService.transferFunds(senderAccNo, recieverAccNo, amount);

		            if (newBalance>-1) {
		            	RequestDispatcher acsDispatcher = request.getRequestDispatcher("WEB-INF/views/Transfer.jsp?success=Transfer Successfull. New Balance is "+newBalance);
		    			acsDispatcher.forward(request, response);
		            }else if(newBalance == -10) {
		            	RequestDispatcher acfDispatcher = request.getRequestDispatcher("WEB-INF/views/Transfer.jsp?error=Insufficient Balance");
		    			acfDispatcher.forward(request, response);
		            }
		            else {
		            	RequestDispatcher acfDispatcher = request.getRequestDispatcher("WEB-INF/views/Transfer.jsp?error=Failed to Transfer");
		    			acfDispatcher.forward(request, response);
		            }

		        } catch (Exception e) {
		            e.printStackTrace();
		            RequestDispatcher acfeDispatcher = request.getRequestDispatcher("WEB-INF/views/Transfer.jsp?error=Something went wrong");
					acfeDispatcher.forward(request, response);
		        }
				break;
				
			case "UPI":
				try {
		            // Collect form data
					Customer sender = (Customer) session.getAttribute("customer");
					
		            String senderUpiId =sender.getUpiId();
		            String recieverUpiId = request.getParameter("upi");
		            double amount = Double.valueOf(request.getParameter("amount"));

		            // Insert into DB
		            double newBalance = customerService.UpiTransfer(senderUpiId, recieverUpiId, amount);

		            if (newBalance>-1) {
		            	RequestDispatcher acsDispatcher = request.getRequestDispatcher("WEB-INF/views/UpiTransaction.jsp?success=Transfer Successfull. New Balance is "+newBalance);
		    			acsDispatcher.forward(request, response);
		            }else if(newBalance == -10) {
		            	RequestDispatcher acfDispatcher = request.getRequestDispatcher("WEB-INF/views/UpiTransaction.jsp?error=Insufficient Balance");
		    			acfDispatcher.forward(request, response);
		            }else if(newBalance == -20) {
		            	RequestDispatcher acfDispatcher = request.getRequestDispatcher("WEB-INF/views/UpiTransaction.jsp?error=Invalid UPI ID");
		    			acfDispatcher.forward(request, response);
		            }
		            else {
		            	RequestDispatcher acfDispatcher = request.getRequestDispatcher("WEB-INF/views/UpiTransaction.jsp?error=Failed to Transfer");
		    			acfDispatcher.forward(request, response);
		            }

		        } catch (Exception e) {
		            e.printStackTrace();
		            RequestDispatcher acfeDispatcher = request.getRequestDispatcher("WEB-INF/views/UpiTransaction.jsp?error=Something went wrong");
					acfeDispatcher.forward(request, response);
		        }
				break;
				
			default:
				RequestDispatcher dispatcher2 = request.getRequestDispatcher("WEB-INF/views/CustomerDashboard.jsp");
				dispatcher2.forward(request, response);
			}
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
}
