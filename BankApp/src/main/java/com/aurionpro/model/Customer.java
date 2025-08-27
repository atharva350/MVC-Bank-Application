package com.aurionpro.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Customer {
	
	private int customerId;
    private String email;
    private String password;   
    private String firstName;
    private String lastName;
    private String gender;    
    private Date dob;
    private String aadhar;
    private String pan;
    private String contactNo;
    private Timestamp createdAt;
    private boolean isDeleted;
    private String upiId;
    
    private List<Account> accounts = new ArrayList<>();
	
    public Customer() {
		super();
	}

	public Customer(String email, String password, String firstName, String lastName, String gender, Date dob,
			String aadhar, String pan, String contactNo) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.dob = dob;
		this.aadhar = aadhar;
		this.pan = pan;
		this.contactNo = contactNo;
	}

	

	public Customer(int customerId, String email, String password, String firstName, String lastName, String gender,
			Date dob, String aadhar, String pan, String contactNo, Timestamp createdAt, boolean isDeleted, String upiId,
			List<Account> accounts) {
		super();
		this.customerId = customerId;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.dob = dob;
		this.aadhar = aadhar;
		this.pan = pan;
		this.contactNo = contactNo;
		this.createdAt = createdAt;
		this.isDeleted = isDeleted;
		this.upiId = upiId;
		this.accounts = accounts;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date date) {
		this.dob = date;
	}

	public String getAadhar() {
		return aadhar;
	}

	public void setAadhar(String aadhar) {
		this.aadhar = aadhar;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp timestamp) {
		this.createdAt = timestamp;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public List<Account> getAccounts() { 
		return accounts; 
	}
    public void setAccounts(List<Account> accounts) { 
    	this.accounts = accounts; 
    }

    public int getAccountCount() {
        return accounts != null ? accounts.size() : 0;
    }

	public String getUpiId() {
		return upiId;
	}

	public void setUpiId(String upiId) {
		this.upiId = upiId;
	}
    
    
    
}
