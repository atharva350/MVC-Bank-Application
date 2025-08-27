package com.aurionpro.model;

import java.sql.Timestamp;

public class Transaction {
	private long transactionId;
    private Timestamp transactionTime;
    private long senderAcc;
    private long receiverAcc;
    private double amount;
    private String transactionType;
    private double senderPreBalance;
    private double senderNewBalance;
    private double receiverPreBalance;
    private double receiverNewBalance;
    private String senderUpiId;
    private String receiverUpiId;
    private double newBalance;
    private double previousBalance;

    private double balance; // computed based on accNo
	public double getSenderPreBalance() {
		return senderPreBalance;
	}
	public void setSenderPreBalance(double senderPreBalance) {
		this.senderPreBalance = senderPreBalance;
	}
	public double getSenderNewBalance() {
		return senderNewBalance;
	}
	public void setSenderNewBalance(double senderNewBalance) {
		this.senderNewBalance = senderNewBalance;
	}
	public double getReceiverPreBalance() {
		return receiverPreBalance;
	}
	public void setReceiverPreBalance(double receiverPreBalance) {
		this.receiverPreBalance = receiverPreBalance;
	}
	public double getReceiverNewBalance() {
		return receiverNewBalance;
	}
	public void setReceiverNewBalance(double receiverNewBalance) {
		this.receiverNewBalance = receiverNewBalance;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public Timestamp getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(Timestamp transactionTime) {
		this.transactionTime = transactionTime;
	}
	public long getSenderAcc() {
		return senderAcc;
	}
	public void setSenderAcc(long senderAcc) {
		this.senderAcc = senderAcc;
	}
	public long getReceiverAcc() {
		return receiverAcc;
	}
	public void setReceiverAcc(long receiverAcc) {
		this.receiverAcc = receiverAcc;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getSenderUpiId() {
		return senderUpiId;
	}
	public void setSenderUpiId(String senderUpiId) {
		this.senderUpiId = senderUpiId;
	}
	public String getReceiverUpiId() {
		return receiverUpiId;
	}
	public void setReceiverUpiId(String receiverUpiId) {
		this.receiverUpiId = receiverUpiId;
	}
	public double getNewBalance() {
		return newBalance;
	}
	public void setNewBalance(double newBalance) {
		this.newBalance = newBalance;
	}
	public double getPreviousBalance() {
		return previousBalance;
	}
	public void setPreviousBalance(double previousBalance) {
		this.previousBalance = previousBalance;
	}
	
}
