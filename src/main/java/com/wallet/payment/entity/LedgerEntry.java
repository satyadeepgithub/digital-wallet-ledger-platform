package com.wallet.payment.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "ledger_entries")
public class LedgerEntry {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name = "wallet_id", nullable = false)
	private Wallet wallet;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "payment_id")
	private Payment payment;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private LedgerEntryType type;
	
	@Column(nullable = false,
			precision=19, scale = 2)
	private BigDecimal amount;
	@Column(nullable = false,
			precision=19, scale = 2)
	private BigDecimal balanceAfter;
	@Column
	private LocalDateTime createdAt;
	
	public LedgerEntry() {}

	public LedgerEntry( Wallet wallet,  Payment payment,LedgerEntryType type, BigDecimal amount, BigDecimal balanceAfter) {
		this.wallet = wallet;
		this.payment = payment;
		this.type = type;
		this.amount = amount;
		this.balanceAfter = balanceAfter;
	}
	
	@PrePersist
	protected void onCreate()
	{
		createdAt = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	
	public Wallet getWallet() {
		return wallet;
	}

	
	public LedgerEntryType getType() {
		return type;
	}

	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public BigDecimal getBalanceAfter() {
		return balanceAfter;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	


}
