package com.aurionpro.service;

import com.aurionpro.Dao.AdminDao;
import com.aurionpro.Dao.CustomerDao;
import com.aurionpro.model.Admin;
import com.aurionpro.model.Customer;

public class LoginService {
	private AdminDao adminDao;
	private CustomerDao customerDao;
	
	public LoginService(AdminDao adminDao, CustomerDao customerDao) {
		super();
		this.adminDao = adminDao;
		this.customerDao = customerDao;
	}
	
	public Admin loginAdmin(String email, String password) {
		return adminDao.login(email,password);
		
	}
	
	public Customer loginCustomer(String email, String password) {
		return customerDao.login(email,password);
		
	}

}
