package com.meritamerica.assignment4;

import java.util.ArrayList;

public class AccountHolder implements Comparable<AccountHolder> {
    private static final double COMBINED_BALANCE_LIMIT = 250000.0;

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

    public CheckingAccount addCheckingAccount(double openingBalance) throws ExceedsCombinedBalanceLimitException {
	CheckingAccount checkingAccount = new CheckingAccount(openingBalance);
	return this.addCheckingAccount(checkingAccount);
    }

    public CheckingAccount addCheckingAccount(CheckingAccount checkingAccount)
	    throws ExceedsCombinedBalanceLimitException {
	if (this.getCombinedBalance() > COMBINED_BALANCE_LIMIT) {
	    throw new ExceedsCombinedBalanceLimitException();
	}

	CheckingAccount[] newCheckingAccounts = new CheckingAccount[this.checkingAccounts.length + 1];
	for (int i = 0; i < this.checkingAccounts.length; i++) {
	    newCheckingAccounts[i] = this.checkingAccounts[i];
	}
	newCheckingAccounts[this.checkingAccounts.length] = checkingAccount;
	this.checkingAccounts = newCheckingAccounts;

	// Also add account to Merit Bank's map of accounts.
	MeritBank.addBankAccount(checkingAccount);

	// Create a deposit transaction with the opening balance.
	DepositTransaction transaction = new DepositTransaction(checkingAccount, checkingAccount.getBalance());
	transaction.setTransactionDate(checkingAccount.getOpenedOn());
	checkingAccount.addTransaction(transaction);

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

    public SavingsAccount addSavingsAccount(double openingBalance) throws ExceedsCombinedBalanceLimitException {
	SavingsAccount savingsAccount = new SavingsAccount(openingBalance);
	return this.addSavingsAccount(savingsAccount);
    }

    public SavingsAccount addSavingsAccount(SavingsAccount savingsAccount) throws ExceedsCombinedBalanceLimitException {
	if (this.getCombinedBalance() > COMBINED_BALANCE_LIMIT) {
	    throw new ExceedsCombinedBalanceLimitException();
	}

	SavingsAccount[] newSavingsAccounts = new SavingsAccount[this.savingsAccounts.length + 1];
	for (int i = 0; i < this.savingsAccounts.length; i++) {
	    newSavingsAccounts[i] = this.savingsAccounts[i];
	}
	newSavingsAccounts[this.savingsAccounts.length] = savingsAccount;
	this.savingsAccounts = newSavingsAccounts;

	// Also add account to Merit Bank's map of accounts.
	MeritBank.addBankAccount(savingsAccount);

	// Create a deposit transaction with the opening balance.
	DepositTransaction transaction = new DepositTransaction(savingsAccount, savingsAccount.getBalance());
	transaction.setTransactionDate(savingsAccount.getOpenedOn());
	savingsAccount.addTransaction(transaction);

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

    public CDAccount addCDAccount(CDOffering offering, double openingBalance)
	    throws ExceedsFraudSuspicionLimitException {
	if (offering == null)
	    return null;

	CDAccount cdAccount = new CDAccount(offering, openingBalance);
	return this.addCDAccount(cdAccount);
    }

    public CDAccount addCDAccount(CDAccount cdAccount) throws ExceedsFraudSuspicionLimitException {
	CDAccount[] newCDAccounts = new CDAccount[this.cdAccounts.length + 1];
	for (int i = 0; i < this.cdAccounts.length; i++) {
	    newCDAccounts[i] = this.cdAccounts[i];
	}
	newCDAccounts[this.cdAccounts.length] = cdAccount;
	this.cdAccounts = newCDAccounts;

	// Also add account to Merit Bank's map of accounts.
	MeritBank.addBankAccount(cdAccount);

	// Get opening balance to check for fraud suspicion (amount exceeds $1000)
	double openingBalance = cdAccount.getBalance();
	if (openingBalance > MeritBank.FRAUD_LIMIT) {
	    throw new ExceedsFraudSuspicionLimitException();
	}

	// Create a deposit transaction with the opening balance.
	DepositTransaction transaction = new DepositTransaction(cdAccount, cdAccount.getBalance());
	transaction.setTransactionDate(cdAccount.getOpenedOn());
	cdAccount.addTransaction(transaction);

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
	    ArrayList<String> transactionLines = new ArrayList<String>();

	    int numberOfLine = 0;
	    // Parse Account Holder first identifier line: Last,Middle,First,SSN
	    String[] ahArray = ahLineArray[numberOfLine].split(",");
	    AccountHolder accountHolder = new AccountHolder(ahArray[2], ahArray[1], ahArray[0], ahArray[3]);
	    numberOfLine++;

	    // Parse #ctas checks
	    int numberOfCheckingAccounts = Integer.parseInt(ahLineArray[numberOfLine]);
	    numberOfLine++;

	    // Parse Checking accounts: Acct#, balance, interest rate, openingDate
	    for (int accountCounter = 0; accountCounter < numberOfCheckingAccounts; accountCounter++) {
		// Use next line to create account.
		String accountData = ahLineArray[numberOfLine++];
		accountHolder.addCheckingAccount(CheckingAccount.readFromString(accountData));

		// Get next line which represents the number of transactions.
		int transactionCount = Integer.parseInt(ahLineArray[numberOfLine++]);

		// Skip first transaction.
		numberOfLine++;

		// Add the lines where transactions are included to the running list
		// of all transaction lines for the account holder.
		for (int transactionCounter = 1; transactionCounter < transactionCount; transactionCounter++) {
		    transactionLines.add(ahLineArray[numberOfLine++]);
		}
	    }

	    // Parse #ctas savings
	    int numberOfSavingsAccounts = Integer.parseInt(ahLineArray[numberOfLine]);
	    numberOfLine++;

	    // Parse Savings accounts: Acct#, balance, interest rate, openingDate
	    for (int accountCounter = 0; accountCounter < numberOfSavingsAccounts; accountCounter++) {
		// Use next line to create account.
		String accountData = ahLineArray[numberOfLine++];
		accountHolder.addSavingsAccount(SavingsAccount.readFromString(accountData));

		// Get next line which represents the number of transactions.
		int transactionCount = Integer.parseInt(ahLineArray[numberOfLine++]);

		// Skip first transaction.
		numberOfLine++;

		// Add the lines where transactions are included to the running list
		// of all transaction lines for the account holder.
		for (int transactionCounter = 1; transactionCounter < transactionCount; transactionCounter++) {
		    transactionLines.add(ahLineArray[numberOfLine++]);
		}
	    }

	    // Parse #ctas CD's
	    int numberOfCDAccounts = Integer.parseInt(ahLineArray[numberOfLine]);
	    numberOfLine++;

	    // Parse CD accounts: Acct#, balance, interest rate, openingDate
	    for (int accountCounter = 0; accountCounter < numberOfCDAccounts; accountCounter++) {
		// Use next line to create account.
		String accountData = ahLineArray[numberOfLine++];
		accountHolder.addCDAccount(CDAccount.readFromString(accountData));

		// Get next line which represents the number of transactions.
		int transactionCount = Integer.parseInt(ahLineArray[numberOfLine++]);

		// Skip first transaction.
		numberOfLine++;

		// Add the lines where transactions are included to the running list
		// of all transaction lines for the account holder.
		for (int transactionCounter = 1; transactionCounter < transactionCount; transactionCounter++) {
		    transactionLines.add(ahLineArray[numberOfLine++]);
		}
	    }

	    for (int transactionIndex = 0; transactionIndex < transactionLines.size(); transactionIndex++) {
		String transactionLine = transactionLines.get(transactionIndex);
		Transaction transaction = Transaction.readFromString(transactionLine);

		BankAccount sourceAccount = transaction.getSourceAccount();
		if (sourceAccount != null) {
		    sourceAccount.addTransaction(transaction);
		} else {
		    BankAccount targetAccount = transaction.getTargetAccount();
		    targetAccount.addTransaction(transaction);
		}

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
	for (int i = 0; i < numberOfCheckingAccounts; i++) {
	    output += checkingAccountArray[i].writeToString() + "\n";
	}

	// Write savings accounts that belongs to the account holder to a string
	int numberOfSavingsAccounts = this.getNumberOfSavingsAccounts();
	SavingsAccount[] savingsAccountArray = this.getSavingsAccounts();
	output += numberOfSavingsAccounts + "\n";
	for (int i = 0; i < numberOfSavingsAccounts; i++) {
	    output += savingsAccountArray[i].writeToString() + "\n";
	}

	// Write savings accounts that belongs to the account holder to a string
	int numberOfCDAccounts = this.getNumberOfCDAccounts();
	CDAccount[] cdAccountArray = this.getCDAccounts();
	output += numberOfCDAccounts + "\n";
	for (int i = 0; i < numberOfCDAccounts; i++) {
	    output += cdAccountArray[i].writeToString() + "\n";
	}

	return output.trim();
    }

    @Override
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