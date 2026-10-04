package com.wallet.payment.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class WalletCreditRequest {
	
	@NotNull(message = "Amount is required")
	@DecimalMin(value = "0.01",message = "Amount must be greater than Zero")
	private BigDecimal amount;

	public WalletCreditRequest() {
		
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	
	

}
