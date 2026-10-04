package com.wallet.payment.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
	@ExceptionHandler(WalletNotActiveException.class)
	public ResponseEntity<?> handleWalletNotActive(WalletNotActiveException ex){
		Map<String,Object> response = Map.of(
				"timestamp",LocalDateTime.now(),
				"error","Wallet Not Active",
				"status",HttpStatus.CONFLICT.value(),
				"message",ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}
	@ExceptionHandler(MethodArgumentNotValidException .class)
	public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException  ex){
		Map <String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().
		forEach(error-> errors.put(error.getField(), error.getDefaultMessage()));
		Map<String,Object> response = Map.of(
				"timestamp",LocalDateTime.now(),
				"error","Validation Failed",
				"status",HttpStatus.BAD_REQUEST.value(),
				"messages",errors);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}
	
	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<?> handleInsufficientBalance(InsufficientBalanceException ex){
		Map <String,Object> response = Map.of(
				"timestamp",LocalDateTime.now(),
				"error","Insufficient Balance",
				"status",HttpStatus.BAD_REQUEST.value(),
				"message",ex.getMessage());
		
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	@ExceptionHandler(InvalidTransferException.class)
	public ResponseEntity<Map<String, Object>> handleInvalidTransfer(
	        InvalidTransferException ex) {

	    Map<String, Object> response = new HashMap<>();

	    response.put("timestamp", LocalDateTime.now());
	    response.put("status", HttpStatus.BAD_REQUEST.value());
	    response.put("error", "Invalid Transfer");
	    response.put("message", ex.getMessage());

	    return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body(response);
	}
	@ExceptionHandler(IdempotencyConflictException.class)
	public ResponseEntity<Map<String, Object>> handleIdempotencyConflict(
	        IdempotencyConflictException ex) {

	    Map<String, Object> response = new HashMap<>();

	    response.put("timestamp", LocalDateTime.now());
	    response.put("status", HttpStatus.CONFLICT.value());
	    response.put("error", "Idempotency Conflict");
	    response.put("message", ex.getMessage());

	    return ResponseEntity
	            .status(HttpStatus.CONFLICT)
	            .body(response);
	}
	

}
