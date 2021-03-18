package com.meritamerica.assignment4;

public class TransferTransaction extends Transaction {

	TransferTransaction(BankAccount sourceAccount, BankAccount targetAccount, double amount){
		this.sourceAccount = sourceAccount;
		this.targetAccount = targetAccount;
		this.amount = amount;
	}

	@Override
	public void process() throws ExceedsFraudSuspicionLimitexception {
		// TODO Auto-generated method stub
		
	}
}
