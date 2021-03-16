package com.meritamerica.assignment4;

public class CDOffering{

	  // Instance Variables
    private int term;
    private double interestRate;

    // Constructor with parameters
    public CDOffering(int term, double interestRate) {
	this.term = term;
	this.interestRate = interestRate;
    }

    public int getTerm() {
	return this.term;
    }

    public double getInterestRate() {
	return this.interestRate;
    }

    public static CDOffering readFromString(String cdOfferingDataString) {
	String[] arrayCD = cdOfferingDataString.split(",");
	try {
	    return new CDOffering(Integer.parseInt(arrayCD[0]), Double.parseDouble(arrayCD[1]));
	} catch (NumberFormatException ex) {
	    throw ex;
	}
    }

    public String writeToString() {
	return this.getTerm() + "," + this.getInterestRate();
    }
}
