package com.meritamerica.assignment4;

public class ExceedsFraudSuspicionLimitException extends Exception {
    public ExceedsFraudSuspicionLimitException() {
	super();
    }

    public ExceedsFraudSuspicionLimitException(String message) {
	super(message);
    }
}
