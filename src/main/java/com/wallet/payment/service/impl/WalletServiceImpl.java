package com.wallet.payment.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.wallet.payment.dto.WalletResponse;
import com.wallet.payment.entity.User;
import com.wallet.payment.entity.Wallet;
import com.wallet.payment.entity.WalletStatus;
import com.wallet.payment.exception.DuplicateWalletException;
import com.wallet.payment.exception.UserNotFoundException;
import com.wallet.payment.exception.WalletNotFoundException;
import com.wallet.payment.repository.UserRepository;
import com.wallet.payment.repository.WalletRepository;
import com.wallet.payment.service.WalletService;

@Service
public class WalletServiceImpl implements WalletService{
	
	private final UserRepository userRepository;
	private final WalletRepository walletRepository;
	

	public WalletServiceImpl(UserRepository userRepository, WalletRepository walletRepository) {
		super();
		this.userRepository = userRepository;
		this.walletRepository = walletRepository;
	}

	@Override
	public WalletResponse createWallet(Long userId) {
		
		if(walletRepository.existsById(userId)) {
			throw new DuplicateWalletException("Wallet already exists for user: " + userId);
		}
		User user = userRepository.findById(userId)
				.orElseThrow(()-> new UserNotFoundException(userId));
		
		Wallet wallet = new Wallet(user,BigDecimal.ZERO,"INR",WalletStatus.ACTIVE);
		Wallet savedWallet = walletRepository.save(wallet);
				
		return mapToResponse(savedWallet);
	}

	@Override
	public WalletResponse getWalletByUserId(Long userId) {
		Wallet wallet = walletRepository.findByUserId(userId).
				orElseThrow(()->new WalletNotFoundException(userId));
		return mapToResponse(wallet);
	}
	
	private WalletResponse mapToResponse(Wallet wallet) {
		return new WalletResponse(wallet.getId(),wallet.getUser().getId(),
				wallet.getBalance(),wallet.getCurrency(),wallet.getStatus(),
				wallet.getCreatedAt(),wallet.getUpdatedAt());
	}

}
