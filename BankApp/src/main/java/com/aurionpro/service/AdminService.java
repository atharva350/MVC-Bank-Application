package com.aurionpro.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.aurionpro.Dao.AccountDao;
import com.aurionpro.Dao.AdminDao;
import com.aurionpro.Dao.BranchDao;
import com.aurionpro.Dao.CustomerDao;
import com.aurionpro.Dao.TransactionDao;
import com.aurionpro.model.Account;
import com.aurionpro.model.BankBranch;
import com.aurionpro.model.Customer;
import com.aurionpro.model.Transaction;

public class AdminService {
	private AdminDao adminDao;
	private CustomerDao customerDao;
	private BranchDao branchDao;
	private AccountDao accountDao;
	private TransactionDao transactionDao;
	
	public AdminService(AdminDao adminDao, CustomerDao customerDao, BranchDao branchDao, AccountDao accountDao, TransactionDao transactionDao) {
		super();
		this.adminDao = adminDao;
		this.customerDao = customerDao;
		this.branchDao = branchDao;
		this.accountDao = accountDao;
		this.transactionDao = transactionDao;
	}

	public boolean addCustomer(Customer customer) {
		return customerDao.addCustomer(customer);
	}

	public boolean addBranch(BankBranch branch) {
		return branchDao.addBranch(branch);
	}

	public List<Customer> getAllCustomers() throws SQLException {
		return customerDao.getAllCustomers();
	}

	public List<BankBranch> getAllBranches() throws SQLException {
		return branchDao.getAllBranches();
	}

	public boolean isFirstAccount(int customerId) {
		return accountDao.isFirstAccount(customerId);
	}

	public void disableUpiAccounts(Integer customerId) {
		accountDao.disableUpiAccounts(customerId);
		
	}

	public boolean addAccount(Account account) {
		return accountDao.addAccount(account);
	}

	public long genrateAccountNumber() {
		return accountDao.generateAccountNumber();
	}

	public List<Customer> getAllCustomersWithAccounts() throws SQLException {
		return customerDao.getAllCustomersWithAccounts();
	}

	public List<Transaction> getTransactions(String type, String accNo, Date fromDate, Date toDate, Double minAmount,
			Double maxAmount, String upi) {
		return transactionDao.getTransactions(type, accNo, fromDate, toDate, minAmount, maxAmount, upi);
	}
	
}
