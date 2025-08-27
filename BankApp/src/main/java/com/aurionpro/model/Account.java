package com.aurionpro.model;

import java.sql.Timestamp;

public class Account {
    private long accNo;
    private int customerId;
    private String ifscCode;
    private double balance;
    private Timestamp createdAt;
    private boolean isDeleted;
    private boolean upiEnabled;
    private String branchName;

	// Getters and Setters
    public long getAccNo() {
        return accNo;
    }
    public void setAccNo(long accNo) {
        this.accNo = accNo;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public String getIfscCode() {
        return ifscCode;
    }
    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    public boolean isDeleted() {
        return isDeleted;
    }
    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    public boolean isUpiEnabled() {
        return upiEnabled;
    }
    public void setUpiEnabled(boolean upiEnabled) {
        this.upiEnabled = upiEnabled;
    }
    public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
}
