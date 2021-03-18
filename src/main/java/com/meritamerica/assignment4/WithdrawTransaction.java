package com.meritamerica.assignment4;

public class WithdrawTransaction extends Transaction {

	WithdrawTransaction(BankAccount targetAccount, double amount){
		this.targetAccount = targetAccount;
		this.amount = amount;
	}
	
	@Override
	public void process() throws ExceedsFraudSuspicionLimitexception {
		// TODO Auto-generated method stub
		
	}

	
}
