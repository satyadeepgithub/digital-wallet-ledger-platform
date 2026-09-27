package com.wallet.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.wallet.payment.entity.WalletStatus;

public class WalletResponse {
	
	private Long id;
    private Long userId;
    private BigDecimal balance;
    private String currency;
    private WalletStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
	public WalletResponse() {
		
	}
	public WalletResponse(Long id, Long userId, BigDecimal balance, String currency, WalletStatus status,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		
		this.id = id;
		this.userId = userId;
		this.balance = balance;
		this.currency = currency;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public WalletStatus getStatus() {
		return status;
	}
	public void setStatus(WalletStatus status) {
		this.status = status;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
    
    

}
