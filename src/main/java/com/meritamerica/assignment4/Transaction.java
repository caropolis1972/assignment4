package com.meritamerica.assignment4;

import java.util.Date;

public abstract class Transaction {
    protected static final double FRAUD_LIMIT = 1000.0;

    private BankAccount sourceAccount;
    private BankAccount targetAccount;
    private double amount;
    private Date transactionDate;
    private boolean processedByFraudTeam;
    private String rejectionReason;

    public Transaction(BankAccount targetAccount, double amount) {
	this.targetAccount = targetAccount;
	this.amount = amount;
    }

    public Transaction(BankAccount sourceAccount, BankAccount targetAccount, double amount) {
	this(targetAccount, amount);
	this.sourceAccount = sourceAccount;
    }

    public BankAccount getSourceAccount() {
	return this.sourceAccount;
    }

    public void setSourceAccount(BankAccount sourceAccount) {
	this.sourceAccount = sourceAccount;
    }

    public BankAccount getTargetAccount() {
	return this.targetAccount;
    }

    public void setTargetAccount(BankAccount targetAccount) {
	this.targetAccount = targetAccount;
    }

    public double getAmount() {
	return this.amount;
    }

    public void setAmount(double amount) {
	this.amount = amount;
    }

    public Date getTransactionDate() {
	return this.transactionDate;
    }

    public void setTransactionDate(Date date) {
	this.transactionDate = date;
    }

    public abstract void process()
	    throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException;

    public boolean isProcessedByFraudTeam() {
	return this.processedByFraudTeam;
    }

    public void setProcessedByFraudTeam(boolean isProcessed) {
	this.processedByFraudTeam = isProcessed;
    }

    public String getRejectionReason() {
	return this.rejectionReason;
    }

    public void setRejectionReason(String reason) {
	this.rejectionReason = reason;
    }

    public static Transaction readFromString(String transactionDataString) {
	// TODO Implement read
    }

    public String writeToString() {
	// TODO Implement write
    }
}
