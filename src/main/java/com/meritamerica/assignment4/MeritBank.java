package com.meritamerica.assignment4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MeritBank {
    // Class variables
    private static AccountHolder[] accountHolders = new AccountHolder[0];
    private static CDOffering[] cdOfferings = new CDOffering[0];
    private static long nextAccountNumber = 1;
    private static FraudQueue fraudQueue = new FraudQueue();

    public static void addAccountHolder(AccountHolder accountHolder) {
	AccountHolder[] newAccountHolders = new AccountHolder[accountHolders.length + 1];
	for (int i = 0; i < accountHolders.length; i++) {
	    newAccountHolders[i] = accountHolders[i];
	}
	newAccountHolders[accountHolders.length] = accountHolder;
	accountHolders = newAccountHolders;
    }

    public static AccountHolder[] getAccountHolders() {
	return accountHolders;
    }

    public static CDOffering[] getCDOfferings() {
	return cdOfferings;
    }

    public static void setCDOfferings(CDOffering[] offerings) {
	cdOfferings = offerings;
    }

    public static CDOffering getBestCDOffering(double depositAmount) {
	if (cdOfferings == null || cdOfferings.length == 0) {
	    return null;
	}

	CDOffering bestCDOffering = cdOfferings[0];
	double bestFutureValue = futureValue(depositAmount, bestCDOffering.getInterestRate(), bestCDOffering.getTerm());

	for (int i = 1; i < cdOfferings.length; i++) {
	    CDOffering offering = cdOfferings[i];
	    double offeringFutureValue = futureValue(depositAmount, offering.getInterestRate(), offering.getTerm());
	    if (offeringFutureValue > bestFutureValue) {
		bestFutureValue = offeringFutureValue;
		bestCDOffering = offering;
	    }
	}

	return bestCDOffering;
    }

    public static CDOffering getSecondBestCDOffering(double depositAmount) {
	if (cdOfferings == null || cdOfferings.length <= 1) {
	    return null;
	}

	CDOffering bestCDOffering = cdOfferings[0];
	double bestFutureValue = futureValue(depositAmount, bestCDOffering.getInterestRate(), bestCDOffering.getTerm());
	CDOffering secondBestCDOffering = null;
	double secondBestFutureValue = 0;

	for (int i = 1; i < cdOfferings.length; i++) {
	    CDOffering offering = cdOfferings[i];
	    double offeringFutureValue = futureValue(depositAmount, offering.getInterestRate(), offering.getTerm());
	    if (offeringFutureValue > bestFutureValue) {
		secondBestFutureValue = bestFutureValue;
		secondBestCDOffering = bestCDOffering;

		bestFutureValue = offeringFutureValue;
		bestCDOffering = offering;
	    } else if (offeringFutureValue > secondBestFutureValue) {
		secondBestFutureValue = bestFutureValue;
		secondBestCDOffering = bestCDOffering;
	    }
	}

	return secondBestCDOffering;
    }

    public static void clearCDOfferings() {
	cdOfferings = null;

    }

    public static long getNextAccountNumber() {
	return nextAccountNumber++;
    }

    public static void setNextAccountNumber(long nextAccountNumber) {
	MeritBank.nextAccountNumber = nextAccountNumber;
    }

    public static double totalBalances() {
	double totalBalances = 0;
	for (int i = 0; i < accountHolders.length; i++) {
	    totalBalances += accountHolders[i].getCombinedBalance();
	}

	return totalBalances;
    }

    public static double futureValue(double presentValue, double interestRate, int term) {
	return recursiveFutureValue(presentValue, term, interestRate);
    }

    public static double recursiveFutureValue(double amount, int years, double interestRate) {
	return (amount * (pow(1 + interestRate, years)));
    }

    public static double pow(double base, int exponent) {
	if (exponent == 1) {
	    return base;
	} else {
	    return base * pow(base, exponent - 1);
	}
    }

    // Should also read BankAccount transactions and the FraudQueue
    // Read each line of the file and parse it
    public static boolean readFromFile(String fileName) {
	ArrayList<String> lines = new ArrayList<String>();

	try {
	    BufferedReader rd = new BufferedReader(new FileReader(fileName));

	    while (true) {
		// Try reading the next line from file loaded into memory.
		String line = rd.readLine();

		// Check if line was read.
		if (line == null) {
		    break; // EOF
		} else {
		    lines.add(line);
		}
	    }
	    rd.close();
	} catch (IOException ex) {
	    return false;
	}

	int numberOfLine = 0;

	try {
	    // read value and store next bank account number
	    Long nextAccountNumber = Long.parseLong(lines.get(numberOfLine));
	    numberOfLine++;

	    // Parse #of CD's
	    int numberOfCDOfferings = Integer.parseInt(lines.get(numberOfLine));
	    numberOfLine++;

	    // Parse CD offerings: term, interest rate
	    CDOffering[] cdOfferingsArray = new CDOffering[numberOfCDOfferings];
	    for (int i = 0; i < numberOfCDOfferings; i++) {
		cdOfferingsArray[i] = CDOffering.readFromString(lines.get(numberOfLine));
		numberOfLine++;
	    }

	    // Parse #of Account Holders
	    int numberOfAccountHolders = Integer.parseInt(lines.get(numberOfLine));
	    numberOfLine++;

	    // Parse Account Holders: Multiple Lines
	    AccountHolder[] fileAccountHolders = new AccountHolder[numberOfAccountHolders];
	    for (int i = 0; i < numberOfAccountHolders; i++) {
		ArrayList<String> accountHolderLines = new ArrayList<String>();

		if (numberOfLine >= lines.size()) {
		    break;
		}

		// Process first line of account holder which includes name and SSN.
		accountHolderLines.add(lines.get(numberOfLine));
		numberOfLine++;

		while (numberOfLine < lines.size()) {
		    // Get first character of the line.
		    String nextLineFirstChar = lines.get(numberOfLine).substring(0, 1);
		    try {
			// Attempt to convert the first character to a number to check if reached the
			// end of the current account holder's data.
			Integer.parseInt(nextLineFirstChar);
		    } catch (NumberFormatException e) {
			// If it's not a number, then we already reached the line of the next account
			// holder's name.
			break;
		    }

		    accountHolderLines.add(lines.get(numberOfLine));
		    numberOfLine++;
		}

		fileAccountHolders[i] = AccountHolder.readFromString(String.join("\n", accountHolderLines));
	    }

	    // Set the class properties until now that we have succeeded in reading data
	    // from file.
	    setNextAccountNumber(nextAccountNumber);
	    setCDOfferings(cdOfferingsArray);
	    accountHolders = fileAccountHolders;

	} catch (Exception ex) {
	    return false;
	}

	return true;
    }

    // Should also write BankAccount transactions and the FraudQueue
    public static boolean writeToFile(String fileName) {
	String output = getNextAccountNumber() + "\n";

	// Write CD offerings that are provided by the Merit Bank to a string
	CDOffering[] cdOfferingsArray = getCDOfferings();
	int numberOfCDOfferings = cdOfferingsArray.length;
	output += numberOfCDOfferings + "\n";
	for (int i = 0; i <= numberOfCDOfferings; i++) {
	    output += cdOfferingsArray[i].writeToString() + "\n";
	}

	// Write account holders managed by the Merit Bank to a string
	AccountHolder[] accountHoldersArray = getAccountHolders();
	int numberOfAccountHolders = accountHoldersArray.length;
	output += numberOfAccountHolders + "\n";
	for (int i = 0; i <= numberOfAccountHolders; i++) {
	    output += accountHoldersArray[i].writeToString() + "\n";
	}

	// Write string to file.
	try {
	    FileOutputStream outputStream = new FileOutputStream(fileName);
	    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
	    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

	    bufferedWriter.write(output);
	    bufferedWriter.close();
	} catch (IOException e) {
	    e.printStackTrace();
	    return false;
	}
	return true;
    }

    public static AccountHolder[] sortAccountHolders() {
	/* Sort statement */
	Collections.sort(Arrays.asList(getAccountHolders()));
	return getAccountHolders();
    }

    public static boolean processTransaction(Transaction transaction)
	    throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {
	boolean result = true;

	try {
	    // Process transaction to check whether it violates any constraints.
	    transaction.process();
	} catch (ExceedsFraudSuspicionLimitException ex) {
	    FraudQueue queue = getFraudQueue();
	    queue.addTransaction(transaction);

	    result = false;
	}

	return result;
    }

    public static FraudQueue getFraudQueue() {
	return fraudQueue;
    }

    public static BankAccount getBankAccount(long accountId) {
	// Return null when account not found.
	return null;
    }

}
