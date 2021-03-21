package com.meritamerica.assignment4;

import java.util.Date;

public abstract class Transaction {
    private BankAccount sourceAccount;
    private BankAccount targetAccount;
    private double amount;

    public BankAccount getSourceAccount() {

    }

    public void setSourceAccount(BankAccount sourceAccount) {

    }

    public BankAccount getTargetAccount() {

    }

    public void setTargetAccount(BankAccount targetAccount) {

    }

    public double getAmount() {

    }

    public void setAmount(double amount) {

    }

    public Date getTransactionDate() {

    }

    public void setTransactionDate(Date date) {

    }

    public abstract void process()
	    throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException;

    public void setProcessedByFraudTeam(boolean isProcessed) {

    }

    public String getRejectionReason() {

    }

    public void setRejectionReason(String reason) {

    }

    public static Transaction readFromString(String transactionDataString) {

    }

    public String writeToString() {

    }
}
