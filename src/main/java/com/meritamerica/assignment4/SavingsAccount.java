package com.meritamerica.assignment4;

import java.text.ParseException;
import java.util.Date;

public class SavingsAccount extends BankAccount {
    // Class Constants
    private static final double INTEREST_RATE = 0.01;
    protected static final int DATA_COUNT = 4;

    public SavingsAccount(double openingBalance) {
	super(openingBalance, INTEREST_RATE);
    }

    // Constructor with parameters
    public SavingsAccount(long accountNumber, double openingBalance, double interestRate, Date accountOpenedOn) {
	super(accountNumber, openingBalance, interestRate, accountOpenedOn);
    }

    public String toString() {
	String output = "Savings Account Balance: $" + this.getBalance() + "\n";
	output += "Savings Account Interest Rate: " + String.format("%.2f", this.getInterestRate()) + "\n";
	output += "Savings Account Balance in 3 years: $" + String.format("%.2f", this.futureValue(3));
	return output;
    }

    public static SavingsAccount readFromString(String accountData) throws ParseException {
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

	// Create account using header data.
	SavingsAccount bankAccount = new SavingsAccount(accountNumber, balance, interestRate, accountOpenedOn);

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
//
	return bankAccount;
    }
}