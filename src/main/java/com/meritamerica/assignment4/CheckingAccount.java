package com.meritamerica.assignment4;

import java.text.ParseException;
import java.util.Date;

public class CheckingAccount extends BankAccount{

	  // Class Constant
    private static final double INTEREST_RATE = 0.0001;

    // Constructor with parameters
    public CheckingAccount(double openingBalance) {
	super(openingBalance, INTEREST_RATE);
    }

    // Constructor with parameters
    public CheckingAccount(long accountNumber, double openingBalance, double interestRate, Date accountOpenedOn) {
	super(accountNumber, openingBalance, interestRate, accountOpenedOn);
    }

    public static CheckingAccount readFromString(String accountData) throws ParseException {
	BankAccount bankAccount = BankAccount.readFromString(accountData);
	return new CheckingAccount(bankAccount.getAccountNumber(), bankAccount.getBalance(),
		bankAccount.getInterestRate(), bankAccount.getOpenedOn());
    }

    public String toString() {
	String output = "Checking Account Balance: $" + this.getBalance() + "\n";
	output += "Checking Account Interest Rate: " + String.format("%.4f", this.getInterestRate()) + "\n";
	output += "Checking Account Balance in 3 years: $" + String.format("%.2f", this.futureValue(3));
	return output;
    }
}
