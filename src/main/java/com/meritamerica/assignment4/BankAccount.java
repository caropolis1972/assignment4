package com.meritamerica.assignment4;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public abstract class BankAccount {
    protected static final int MIN_TRANSACTIONS = 1;

    // Instance variables
    private long accountNumber;
    private double balance;
    private double interestRate;
    private Date accountOpenedOn;

    private ArrayList<Transaction> transactions;

    // Constructor w/ 2 parameters
    public BankAccount(double balance, double interestRate) {
	this(balance, interestRate, new Date());
    }

    // Constructor w/3 parameters
    public BankAccount(double balance, double interestRate, Date accountOpenedOn) {
	this(MeritBank.getNextAccountNumber(), balance, interestRate, accountOpenedOn);
    }

    // Constructor w/4 parameters
    public BankAccount(long accountNumber, double balance, double interestRate, Date accountOpenedOn) {
	this.accountNumber = accountNumber;
	this.balance = balance;
	this.interestRate = interestRate;
	this.accountOpenedOn = accountOpenedOn;
	this.transactions = new ArrayList<Transaction>();
    }

    public long getAccountNumber() {
	return this.accountNumber;
    }

    public double getBalance() {
	return this.balance;
    }

    public double getInterestRate() {
	return this.interestRate;
    }

    public Date getOpenedOn() {
	return this.accountOpenedOn;
    }

    public boolean withdraw(double amount) {
	if (amount >= 0 && amount <= this.balance) {
	    this.balance -= amount;
	    return true;
	}
	// Withdraw amount was negative or greater than balance
	return false;
    }

    public boolean deposit(double amount) {
	if (amount >= 0) {
	    this.balance += amount;
	    return true;
	}
	// Deposit amount was negative
	return false;
    }

    public void addTransaction(Transaction transaction) {
	this.transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
	return this.transactions;
    }

    public double futureValue(int years) {
	return (this.balance * (MeritBank.pow(1 + this.getInterestRate(), years)));
    }

    public String writeToString() {
	DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
	df.setMaximumFractionDigits(4);

	String output = this.getAccountNumber() + "," + this.getBalance() + "," + df.format(this.getInterestRate())
		+ "," + MeritBank.DATE_FORMAT.format(this.getOpenedOn()) + "\n";

	// Get list of transactions.
	List<Transaction> transactions = this.getTransactions();

	// Append the number of transactions to the output.
	output += transactions.size() + "\n";

	// Append one line per transaction.
	for (int transactionIndex = 0; transactionIndex < transactions.size(); transactionIndex++) {
	    Transaction transaction = transactions.get(transactionIndex);
	    output += transaction.writeToString() + "\n";
	}

	return output.trim();
    }
}
