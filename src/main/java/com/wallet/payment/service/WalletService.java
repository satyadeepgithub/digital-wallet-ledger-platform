package com.wallet.payment.service;

import java.math.BigDecimal;

import com.wallet.payment.dto.TransferRequest;
import com.wallet.payment.dto.TransferResponse;
import com.wallet.payment.dto.WalletResponse;

public interface WalletService {
	
	WalletResponse createWallet(Long userId);
	
	WalletResponse getWalletByUserId(Long userId);
	
	WalletResponse creditWallet(Long userId, BigDecimal amount);
	
	WalletResponse debitWallet(Long userId, BigDecimal amount);
	
	TransferResponse transfer(TransferRequest request, String idempotencyKey);

}
