package com.wallet.payment.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?>handleUserNotFound(UserNotFoundException ex){
		
		Map<String,Object> response = Map.of(
				"timestamp",LocalDateTime.now(),
				"status",HttpStatus.NOT_FOUND.value(),
				"error","User not Found",
				"message",ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
				
	}
	@ExceptionHandler(DuplicateUserException.class)
	public ResponseEntity<?> handleDuplicateUser(DuplicateUserException ex){
		Map<String,Object> response = Map.of(
				"timestamp",LocalDateTime.now(),
				"error","Duplicate User",
				"status",HttpStatus.CONFLICT.value(),
				"message",ex.getMessage()
				);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}
	@ExceptionHandler(DuplicateWalletException.class)
	public ResponseEntity<?> handleDuplicateWallet(DuplicateWalletException ex){
		Map <String,Object> response = Map.of(
				"timestamp",LocalDateTime.now(),
				"error","Duplicate Wallet",
				"status",HttpStatus.CONFLICT.value(),
				"message",ex.getMessage()
				);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}
	@ExceptionHandler(WalletNotFoundException.class)
	public ResponseEntity<?> handleWalletNotFound(WalletNotFoundException ex){
		Map<String,Object> response = Map.of(
				"timestamp",LocalDateTime.now(),
				"error","Wallet Not Found",
				"status",HttpStatus.NOT_FOUND.value(),
				"message",ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	

}
