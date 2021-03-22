package com.meritamerica.assignment4;

import java.text.ParseException;
import java.util.Date;

public class CDAccount extends BankAccount {
    // Class Constant
    private static final int DATA_COUNT = 5;

    // Instance Variables
    private int term;

    // Constructor with parameters
    public CDAccount(CDOffering offering, double balance) {
	super(balance, offering.getInterestRate());
	this.term = offering.getTerm();
    }

    // Constructor with parameters
    public CDAccount(long accountNumber, double openingBalance, double interestRate, Date accountOpenedOn, int term) {
	super(accountNumber, openingBalance, interestRate, accountOpenedOn);
	this.term = term;
    }

    public int getTerm() {
	return this.term;
    }

    public boolean withdraw(double amount) {
	// Withdraw amount was negative or greater than balance
	return false;
    }

    public boolean deposit(double amount) {
	// Deposit amount was negative
	return false;
    }

    public double futureValue() {
	return (super.getBalance() * (MeritBank.pow(1 + this.getInterestRate(), this.getTerm())));
    }

    public String writeToString() {
	return super.writeToString() + "," + this.getTerm();
    }

    public static CDAccount readFromString(String accountData) throws ParseException {
	String[] headerData = accountData.split(",");

	// Ensure we have the expected number of values in the header line.
	if (headerData.length != DATA_COUNT) {
	    throw new NumberFormatException();
	}

	// Capture data from header.
	long accountNumber = Long.parseLong(headerData[0]);
	double balance = Double.parseDouble(headerData[1]);
	double interestRate = Double.parseDouble(headerData[2]);
	Date accountOpenedOn = MeritBank.DATE_FORMAT.parse(headerData[3]);
	int term = Integer.parseInt(headerData[4]);

	// Create account using header data.
	CDAccount bankAccount = new CDAccount(accountNumber, balance, interestRate, accountOpenedOn, term);

//	// Get the number of transactions for this account.
//	int transactionCount = Integer.parseInt(lines[lineNumber++]);
//	if (transactionCount < MIN_TRANSACTIONS) {
//	    throw new NumberFormatException();
//	}
//
//	ArrayList<Transaction> transactions = new ArrayList<Transaction>();
//	for (int transactionCounter = 0; transactionCounter < transactionCount; transactionCounter++) {
//	    String transactionLine = lines[lineNumber++];
//	    Transaction transaction = Transaction.readFromString(transactionLine);
//
//	    transactions.add(transaction);
//	}
//
//	for (int i = 0; i < transactions.size(); i++) {
//	    bankAccount.addTransaction(transactions.get(i));
//	}

	return bankAccount;
    }
}
