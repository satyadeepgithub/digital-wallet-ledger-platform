package com.wallet.payment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.payment.dto.WalletResponse;
import com.wallet.payment.service.WalletService;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {
	private final WalletService walletService;
	
	public WalletController(WalletService walletService) {
		this.walletService = walletService;
	}
	
	@PostMapping("/{userId}")
	public ResponseEntity<WalletResponse> createWallet(@PathVariable Long userId){
		WalletResponse walletResponse = walletService.createWallet(userId);
		return ResponseEntity.status(HttpStatus.CREATED).body(walletResponse);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<WalletResponse> getWallet(@PathVariable Long userId){
		WalletResponse walletResponse = walletService.getWalletByUserId(userId);
		return ResponseEntity.ok(walletResponse);
	}

	
	
	

}
