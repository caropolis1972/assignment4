package com.meritamerica.assignment4;

public class AccountHolder {

	 // Instance variables
    private String firstName;
    private String middleName;
    private String lastName;
    private String ssn;
    private CheckingAccount[] checkingAccounts = new CheckingAccount[0];
    private SavingsAccount[] savingsAccounts = new SavingsAccount[0];
    private CDAccount[] cdAccounts = new CDAccount[0];

    // Constructor with parameters
    public AccountHolder(String firstName, String middleName, String lastName, String ssn) {

	this.firstName = firstName;
	this.middleName = middleName;
	this.lastName = lastName;
	this.ssn = ssn;
    }

    // Setters and Getters methods for each instance variable
    public String getFirstName() {
	return this.firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getMiddleName() {
	return this.middleName;
    }

    public void setMiddleName(String middleName) {
	this.middleName = middleName;
    }

    public String getLastName() {
	return this.lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getSSN() {
	return this.ssn;
    }

    public void setSSN(String ssn) {
	this.ssn = ssn;
    }

    public CheckingAccount addCheckingAccount(double openingBalance) {
	CheckingAccount checkingAccount = new CheckingAccount(openingBalance);
	return this.addCheckingAccount(checkingAccount);
    }

    public CheckingAccount addCheckingAccount(CheckingAccount checkingAccount) {
	if (this.getCombinedBalance() > 250000) {
	    return null;
	}

	CheckingAccount[] newCheckingAccounts = new CheckingAccount[this.checkingAccounts.length + 1];
	for (int i = 0; i < this.checkingAccounts.length; i++) {
	    newCheckingAccounts[i] = this.checkingAccounts[i];
	}
	newCheckingAccounts[this.checkingAccounts.length] = checkingAccount;
	this.checkingAccounts = newCheckingAccounts;

	return checkingAccount;
    }

    public CheckingAccount[] getCheckingAccounts() {
	return this.checkingAccounts;
    }

    public int getNumberOfCheckingAccounts() {
	return this.checkingAccounts.length;
    }

    public double getCheckingBalance() {
	double checkingBalance = 0;
	for (int i = 0; i < this.checkingAccounts.length; i++) {
	    checkingBalance += this.checkingAccounts[i].getBalance();
	}

	return checkingBalance;
    }

    public SavingsAccount addSavingsAccount(double openingBalance) {
	SavingsAccount savingsAccount = new SavingsAccount(openingBalance);
	return this.addSavingsAccount(savingsAccount);
    }

    public SavingsAccount addSavingsAccount(SavingsAccount savingsAccount) {
	if (this.getCombinedBalance() > 250000) {
	    return null;
	}

	SavingsAccount[] newSavingsAccounts = new SavingsAccount[this.savingsAccounts.length + 1];
	for (int i = 0; i < this.savingsAccounts.length; i++) {
	    newSavingsAccounts[i] = this.savingsAccounts[i];
	}
	newSavingsAccounts[this.savingsAccounts.length] = savingsAccount;
	this.savingsAccounts = newSavingsAccounts;

	return savingsAccount;
    }

    public SavingsAccount[] getSavingsAccounts() {
	return this.savingsAccounts;
    }

    public int getNumberOfSavingsAccounts() {
	return this.savingsAccounts.length;
    }

    public double getSavingsBalance() {
	double savingsBalance = 0;
	for (int i = 0; i < this.savingsAccounts.length; i++) {
	    savingsBalance += this.savingsAccounts[i].getBalance();
	}

	return savingsBalance;
    }

    public CDAccount addCDAccount(CDOffering offering, double openingBalance) {
	if (offering == null)
	    return null;

	CDAccount cdAccount = new CDAccount(offering, openingBalance);
	return this.addCDAccount(cdAccount);
    }

    public CDAccount addCDAccount(CDAccount cdAccount) {
	CDAccount[] newCDAccounts = new CDAccount[this.cdAccounts.length + 1];
	for (int i = 0; i < this.cdAccounts.length; i++) {
	    newCDAccounts[i] = this.cdAccounts[i];
	}
	newCDAccounts[this.cdAccounts.length] = cdAccount;
	this.cdAccounts = newCDAccounts;

	return cdAccount;
    }

    public CDAccount[] getCDAccounts() {
	return this.cdAccounts;
    }

    public int getNumberOfCDAccounts() {
	return this.cdAccounts.length;
    }

    public double getCDBalance() {
	double cdBalance = 0;
	for (int i = 0; i < this.cdAccounts.length; i++) {
	    cdBalance += this.cdAccounts[i].getBalance();
	}

	return cdBalance;
    }

    public double getCombinedBalance() {
	return this.getCheckingBalance() + this.getSavingsBalance() + this.getCDBalance();
    }

    public static AccountHolder readFromString(String accountHolderData) throws Exception {
	// Split data string using line break (\n) as separator.
	String[] ahLineArray = accountHolderData.split("\n");
	if (ahLineArray.length < 4) {
	    throw new Exception("Invalid Account Holder input data");
	}
	try {
	    int numberOfLine = 0;
	    // Parse Account Holder first identifier line: Last,Middle,First,SSN
	    String[] ahArray = ahLineArray[numberOfLine].split(",");
	    AccountHolder accountHolder = new AccountHolder(ahArray[2], ahArray[1], ahArray[0], ahArray[3]);
	    numberOfLine++;

	    // Parse #ctas checks
	    int numberOfCheckingAccounts = Integer.parseInt(ahLineArray[numberOfLine]);
	    numberOfLine++;

	    // Parse Checking accounts: Acct#, balance, interest rate, openingDate
	    for (int i = 0; i < numberOfCheckingAccounts; i++) {
		accountHolder.addCheckingAccount(CheckingAccount.readFromString(ahLineArray[numberOfLine]));
		numberOfLine++;
	    }

	    // Parse #ctas savings
	    int numberOfSavingsAccounts = Integer.parseInt(ahLineArray[numberOfLine]);
	    numberOfLine++;

	    // Parse Savings accounts: Acct#, balance, interest rate, openingDate
	    for (int i = 0; i < numberOfSavingsAccounts; i++) {
		accountHolder.addSavingsAccount(SavingsAccount.readFromString(ahLineArray[numberOfLine]));
		numberOfLine++;
	    }

	    // Parse #ctas CD's
	    int numberOfCDAccounts = Integer.parseInt(ahLineArray[numberOfLine]);
	    numberOfLine++;

	    // Parse Savings accounts: Acct#, balance, interest rate, openingDate
	    for (int i = 0; i < numberOfCDAccounts; i++) {
		accountHolder.addCDAccount(CDAccount.readFromString(ahLineArray[numberOfLine]));
		numberOfLine++;
	    }
	    return accountHolder;

	} catch (Exception ex) {
	    throw ex;
	}

    }

    public String toString() {
	String output = "Name: " + this.getFirstName() + " " + this.getMiddleName() + " " + this.getLastName() + "\n";
	output += "SSN: " + this.getSSN() + "\n";
	output += this.getCheckingAccounts().toString() + "\n";
	output += this.getSavingsAccounts().toString();
	return output;

    }

    public String writeToString() {
	String output = this.getLastName() + "," + this.getMiddleName() + "," + this.getFirstName() + ","
		+ this.getSSN() + "\n";

	// Write checking accounts that belongs to the account holder to a string
	int numberOfCheckingAccounts = this.getNumberOfCheckingAccounts();
	CheckingAccount[] checkingAccountArray = this.getCheckingAccounts();
	output += numberOfCheckingAccounts + "\n";
	for (int i = 0; i <= numberOfCheckingAccounts; i++) {
	    output += checkingAccountArray[i].writeToString() + "\n";
	}

	// Write savings accounts that belongs to the account holder to a string
	int numberOfSavingsAccounts = this.getNumberOfSavingsAccounts();
	SavingsAccount[] savingsAccountArray = this.getSavingsAccounts();
	output += numberOfSavingsAccounts + "\n";
	for (int i = 0; i <= numberOfSavingsAccounts; i++) {
	    output += savingsAccountArray[i].writeToString() + "\n";
	}

	// Write savings accounts that belongs to the account holder to a string
	int numberOfCDAccounts = this.getNumberOfCDAccounts();
	CDAccount[] cdAccountArray = this.getCDAccounts();
	output += numberOfCDAccounts + "\n";
	for (int i = 0; i <= numberOfCDAccounts; i++) {
	    output += cdAccountArray[i].writeToString() + "\n";
	}

	return output;
    }

    public int compareTo(AccountHolder otherAccountHolder) {
	// Compares this AccountHolder object with otherAccountHolder.
	// Returns a negative integer, zero, or a positive integer as this instance is
	// less than, equal to,
	// or greater than otherAccountHolder.
	// The AccountHolder objects are compared based on their combined balanced.

	if (this.getCombinedBalance() < otherAccountHolder.getCombinedBalance()) {
	    return -1;
	} else if (this.getCombinedBalance() > otherAccountHolder.getCombinedBalance()) {
	    return 1;
	} else {
	    return 0;
	}
    }
}

