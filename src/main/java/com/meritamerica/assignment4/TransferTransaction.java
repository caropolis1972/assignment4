package com.meritamerica.assignment4;

public class TransferTransaction extends Transaction {

    public TransferTransaction(BankAccount sourceAccount, BankAccount targetAccount, double amount) {
	super(sourceAccount, targetAccount, amount);
    }

    @Override
    public void process()
	    throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {

	BankAccount sourceAccount = this.getSourceAccount();
	BankAccount targetAccount = this.getTargetAccount();
	double amount = this.getAmount();

	// 1. Transfer amount should be less than balance in source account
	if (amount > sourceAccount.getBalance()) {
	    throw new ExceedsAvailableBalanceException();
	}

	// 2. Transfer amount should be a positive number
	if (amount < 0) {
	    throw new NegativeAmountException();
	}

	// 3. Transfer amount exceeding $1,000 must be reviewed by the fraud team
	if (amount > FRAUD_LIMIT) {
	    throw new ExceedsFraudSuspicionLimitException();
	}

	// Perform (process) transfer.
	sourceAccount.withdraw(amount);
	targetAccount.deposit(amount);

	// Add transaction to source & target accounts.
	sourceAccount.addTransaction(this);
	targetAccount.addTransaction(this);
    }

}
