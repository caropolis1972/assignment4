package com.meritamerica.assignment4;

import java.util.LinkedList;

public class FraudQueue {
    private LinkedList<Transaction> transactions;

    public FraudQueue() {
	this.transactions = new LinkedList<Transaction>();
    }

    public void addTransaction(Transaction transaction) {
	this.transactions.addLast(transaction);
    }

    public Transaction getTransaction() {
	if (this.transactions.isEmpty()) {
	    return null;
	}

	return this.transactions.removeFirst();
    }

}
