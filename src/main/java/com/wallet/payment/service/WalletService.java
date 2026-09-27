package com.wallet.payment.service;

import com.wallet.payment.dto.WalletResponse;

public interface WalletService {
	
	WalletResponse createWallet(Long userId);
	
	WalletResponse getWalletByUserId(Long userId);

}
