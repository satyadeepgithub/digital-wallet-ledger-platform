package com.wallet.payment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wallet.payment.entity.LedgerEntry;

public interface LedgerEntryRepository extends JpaRepository<LedgerEntry,Long>{

	List<LedgerEntry> findByWalletIdOrderByCreatedAtDesc(Long WalletId);
	
	}
