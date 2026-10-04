package com.wallet.payment.exception;

public class InsufficientBalanceException extends RuntimeException{
	
	public InsufficientBalanceException() {
		super("Insufficient Wallet Balance");
	}

}
