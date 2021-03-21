package com.meritamerica.assignment4;

public class WithdrawTransaction extends Transaction {

    public WithdrawTransaction(BankAccount targetAccount, double amount) {
	super(targetAccount, amount);
    }

    @Override
    public void process()
	    throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {
	// Get the target account where amount will be deposited to.
	BankAccount targetAccount = this.getTargetAccount();

	// Get the amount to deposit.
	double amount = this.getAmount();

	// Check for fraud suspicion (amount exceeds $1000)
	if (amount > FRAUD_LIMIT) {
	    throw new ExceedsFraudSuspicionLimitException();
	}

	// Perform (process) withdraw.
	if (targetAccount.withdraw(amount)) {
	    // Add transaction to target account.
	    targetAccount.addTransaction(this);
	} else {
	    if (amount < 0) {
		throw new NegativeAmountException();
	    } else {
		throw new ExceedsAvailableBalanceException();
	    }
	}
    }

}
