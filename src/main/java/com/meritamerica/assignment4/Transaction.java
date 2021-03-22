package com.meritamerica.assignment4;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public abstract class Transaction {
    protected static final int TRANSACTION_PARTS_COUNT = 4;
    protected static final long SOURCE_ACCOUNT_NUMBER_NON_EXISTENT = -1;

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

    public static Transaction readFromString(String transactionDataString) throws ParseException {
	// Process transaction.
	String[] transactionParts = transactionDataString.split(",");

	if (transactionParts.length != TRANSACTION_PARTS_COUNT) {
	    throw new NumberFormatException();
	}

	// Capture all data from transaction.
	int sourceAccountNumber = Integer.parseInt(transactionParts[0]);
	int targetAccountNumber = Integer.parseInt(transactionParts[1]);
	double amount = Double.parseDouble(transactionParts[2]);
	Date transactionDate = MeritBank.DATE_FORMAT.parse(transactionParts[3]);

	BankAccount targetAccount = MeritBank.getBankAccount(targetAccountNumber);
	if (targetAccount == null) {
	    throw new NumberFormatException();
	}

	Transaction transaction;
	if (sourceAccountNumber == SOURCE_ACCOUNT_NUMBER_NON_EXISTENT) {
	    if (amount >= 0) {
		transaction = new DepositTransaction(targetAccount, amount);
	    } else {
		transaction = new WithdrawTransaction(targetAccount, Math.abs(amount));
	    }
	} else {
	    BankAccount sourceAccount = MeritBank.getBankAccount(sourceAccountNumber);
	    if (sourceAccount == null) {
		throw new NumberFormatException();
	    }

	    transaction = new TransferTransaction(sourceAccount, targetAccount, Math.abs(amount));
	}
	transaction.setTransactionDate(transactionDate);

	return transaction;
    }

    public String writeToString() {
	DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
	df.setMaximumFractionDigits(4);

	long sourceAccountNumber = -1;
	BankAccount sourceAccount = this.getSourceAccount();
	if (sourceAccount != null) {
	    sourceAccountNumber = sourceAccount.getAccountNumber();
	}

	long targetAccountNumber = targetAccount.getAccountNumber();

	String output = sourceAccountNumber + "," + targetAccountNumber + "," + this.getAmount() + ","
		+ MeritBank.DATE_FORMAT.format(this.getTransactionDate());

	return output;
    }
}
