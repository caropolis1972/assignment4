package com.meritamerica.assignment4;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CDAccount extends BankAccount {
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

    public static CDAccount readFromString(String accountData) throws ParseException {
	String[] arrayCD = accountData.split(",");
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	try {

	    return new CDAccount(Long.parseLong(arrayCD[0]), Double.parseDouble(arrayCD[1]),
		    Double.parseDouble(arrayCD[2]), dateFormat.parse(arrayCD[3]), Integer.parseInt(arrayCD[4]));
	} catch (Exception ex) {
	    throw new NumberFormatException(ex.getMessage());
	}
    }

    public String writeToString() {
	return super.writeToString() + "," + this.getTerm();
    }

    public double futureValue() {
	return (super.getBalance() * (Math.pow(1 + this.getInterestRate(), this.getTerm())));
    }

}
