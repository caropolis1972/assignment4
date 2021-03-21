package com.meritamerica.assignment4;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BankAccount {
    // Instance variables
    private long accountNumber;
    private double balance;
    private double interestRate;
    private Date accountOpenedOn;

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

    }

    public List<Transaction> getTransactions() {

    }

    public double futureValue(int years) {
	return (this.balance * (Math.pow(1 + this.getInterestRate(), years)));
    }

    public static BankAccount readFromString(String accountData) throws ParseException {
	String[] arrayCD = accountData.split(",");
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	try {

	    return new BankAccount(Long.parseLong(arrayCD[0]), Double.parseDouble(arrayCD[1]),
		    Double.parseDouble(arrayCD[2]), dateFormat.parse(arrayCD[3]));
	} catch (Exception ex) {
	    throw new NumberFormatException(ex.getMessage());
	}
    }

    public String writeToString() {
	return this.getAccountNumber() + "," + this.getBalance() + "," + this.getInterestRate() + ","
		+ this.getOpenedOn();
    }
}
