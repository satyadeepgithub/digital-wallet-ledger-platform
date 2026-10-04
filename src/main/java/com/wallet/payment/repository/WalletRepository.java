package com.wallet.payment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wallet.payment.entity.Wallet;

import jakarta.persistence.LockModeType;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

	Optional<Wallet> findByUserId(Long userId);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("Select w from Wallet w where w.user.id = :userId")
	Optional<Wallet> findByUserIdForUpdate(@Param("userId") Long userId);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("Select w from Wallet w where w.user.id in :userIds order by w.user.id")
	List<Wallet> findByUserIdsForUpdate(@Param("userIds") List<Long> userIds);

	boolean existsByUserId(Long userId);
}
