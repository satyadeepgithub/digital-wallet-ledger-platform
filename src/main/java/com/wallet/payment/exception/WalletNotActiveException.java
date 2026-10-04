package com.wallet.payment.exception;

public class WalletNotActiveException extends RuntimeException {

    public WalletNotActiveException() {
        super("Wallet is not active");
    }
}