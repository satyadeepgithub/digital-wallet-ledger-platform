package com.wallet.payment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wallet.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment,Long>{
	
	Optional<Payment> findByReference(String reference);
	
	boolean existsByReference(String reference);
	
	Optional<Payment> findByIdempotencyKey(String idempotencyKey);
	
	boolean existsByIdempotencyKey(String idempotencyKey);
}
