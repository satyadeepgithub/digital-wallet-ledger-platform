package com.wallet.payment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.payment.dto.TransferRequest;
import com.wallet.payment.dto.TransferResponse;
import com.wallet.payment.dto.WalletCreditRequest;
import com.wallet.payment.dto.WalletResponse;
import com.wallet.payment.service.WalletService;

import jakarta.validation.Valid;

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
	
	@PostMapping("/{userId}/credit")
	public ResponseEntity<WalletResponse> creditWallet(@PathVariable Long userId,
			@Valid @RequestBody WalletCreditRequest request){
		WalletResponse response = walletService.creditWallet(userId, request.getAmount());
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/{userId}/debit")
	public ResponseEntity<WalletResponse> debitWallet(@PathVariable Long userId,
			@Valid @RequestBody WalletCreditRequest request){
		WalletResponse response = walletService.debitWallet(userId, request.getAmount());
			return ResponseEntity.ok(response);
	}
	
	@PostMapping("/transfer")
	public ResponseEntity<TransferResponse> transfer(@RequestHeader("Idempotency-key") String idempotencyKey,
			@Valid @RequestBody TransferRequest request){
		TransferResponse response = walletService.transfer(request,idempotencyKey);
		return ResponseEntity.ok(response);
	}

	
	
	

}
