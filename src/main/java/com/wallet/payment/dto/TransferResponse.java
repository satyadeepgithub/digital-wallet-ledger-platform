package com.wallet.payment.dto;

import java.math.BigDecimal;

public class TransferResponse {

	private String paymentReference;
    private String status;
    private Long senderUserId;
    private Long receiverUserId;
    private BigDecimal amount;
    private String message;
    private boolean idempotentReplay;

    public TransferResponse() {
    }

    public TransferResponse(
    		String paymentReference,
            String status,
            Long senderUserId,
            Long receiverUserId,
            BigDecimal amount,
            String message,
            Boolean idempotentReplay) {
    	this.paymentReference = paymentReference;
        this.status = status;
        this.senderUserId = senderUserId;
        this.receiverUserId = receiverUserId;
        this.amount = amount;
        this.message=message;
        this.idempotentReplay = idempotentReplay;
    }

    public String getPaymentReference() {
		return paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(Long senderUserId) {
        this.senderUserId = senderUserId;
    }

    public Long getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(Long receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isIdempotentReplay() {
		return idempotentReplay;
	}

	public void setIdempotentReplay(boolean idempotentReplay) {
		this.idempotentReplay = idempotentReplay;
	}
}