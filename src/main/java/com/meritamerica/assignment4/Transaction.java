package com.meritamerica.assignment4;

import java.util.Date.*;

public abstract class Transaction {

	public BankAccount getSourceAccount() {
		return null;
	}
	
	public void setSourceAccount(BankAccount sourceAccount) {
		this.getSourceAccount();
	}
	
	public BankAccount getTargetAccount() {
		return null;
	}
	
	public void setTargetAccount(BankAccount targetAccount) {
		this.getTargetAccount();
	}
	
	public double getAmount() {
		return getAmount();
	}
	
	public void setAmount(double amount) {
		this.getAmount();
	}
	
	public java.util.Date getTransactionDate(){
		return null;
	}
	
	public void setTransactionDate(java.util.Date date) {
		this.getTransactionDate();
	}
	
	public String writeToString() {
		return null;
	}
	
	public static Transaction readFromString(String transactionDateString) {
		return null;
	}
	
	public abstract void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitexception;
	
	public boolean isProcessedByFraudTeam() {
		return null;
	}
	
	public void setProcessedByFraudTeam(boolean isProcessedByFraudTeam) {
		this.isProcessedByFraudTeam();
	}
	
	public String getRejectionReason() {
		return null;
	}
	
	public void setRejectionReason(String reason) {
		this.getRejectionReason();
	}
}
