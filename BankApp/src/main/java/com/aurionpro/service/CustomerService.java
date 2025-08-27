package com.aurionpro.service;

import java.sql.Date;
import java.util.List;

import com.aurionpro.Dao.AccountDao;
import com.aurionpro.Dao.BranchDao;
import com.aurionpro.Dao.CustomerDao;
import com.aurionpro.Dao.TransactionDao;
import com.aurionpro.model.Account;
import com.aurionpro.model.Beneficiary;
import com.aurionpro.model.Customer;
import com.aurionpro.model.Transaction;

public class CustomerService {
	private CustomerDao customerDao;
	private BranchDao branchDao;
	private AccountDao accountDao;
	private TransactionDao transactionDao;
	
	public CustomerService(CustomerDao customerDao, BranchDao branchDao, AccountDao accountDao, TransactionDao transactionDao) {
		super();
		this.customerDao = customerDao;
		this.branchDao = branchDao;
		this.accountDao = accountDao;
		this.transactionDao = transactionDao;
	}

	public boolean addBeneficiary(Beneficiary beneficiary, Customer customer) {
		return customerDao.addBeneficiary(beneficiary, customer);
	}

	public boolean changePassword(String newPassword, Customer customer) {
		return customerDao.changePassword(newPassword, customer);
	}

	public List<Account> getCustomerAccounts(Customer customer) {
		return accountDao.getCustomerAccounts(customer);
	}

	public double deposit(Long accNo, Double amount) {
		return transactionDao.deposit(accNo,amount);
	}

	public double withdraw(long accNo, double amount) {
		return transactionDao.withdraw(accNo,amount);
	}

	public List<Beneficiary> getCustomerBeneficiaries(Customer customer3) {
		return accountDao.getCustomerBeneficiaries(customer3);
	}
	
	public double transferFunds(long senderAccNo, long receiverAccNo, double amount) {
		return transactionDao.transferFunds(senderAccNo, receiverAccNo, amount);
	}
	
	public double UpiTransfer(String senderUpiId, String receiverUpiId, double amount) {
		return transactionDao.upiTransfer(senderUpiId, receiverUpiId, amount);
	}

	public List<Transaction> getPassbookTransactions(long accNo, Date fromDate, Date toDate) {
		return transactionDao.getPassbookTransactions(accNo, fromDate, toDate);
	}
}
