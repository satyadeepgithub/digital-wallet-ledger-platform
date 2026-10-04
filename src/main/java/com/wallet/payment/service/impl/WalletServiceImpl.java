package com.wallet.payment.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wallet.payment.dto.TransferRequest;
import com.wallet.payment.dto.TransferResponse;
import com.wallet.payment.dto.WalletResponse;
import com.wallet.payment.entity.LedgerEntry;
import com.wallet.payment.entity.LedgerEntryType;
import com.wallet.payment.entity.Payment;
import com.wallet.payment.entity.PaymentStatus;
import com.wallet.payment.entity.User;
import com.wallet.payment.entity.Wallet;
import com.wallet.payment.entity.WalletStatus;
import com.wallet.payment.exception.DuplicateWalletException;
import com.wallet.payment.exception.IdempotencyConflictException;
import com.wallet.payment.exception.InsufficientBalanceException;
import com.wallet.payment.exception.InvalidTransferException;
import com.wallet.payment.exception.UserNotFoundException;
import com.wallet.payment.exception.WalletNotActiveException;
import com.wallet.payment.exception.WalletNotFoundException;
import com.wallet.payment.repository.LedgerEntryRepository;
import com.wallet.payment.repository.PaymentRepository;
import com.wallet.payment.repository.UserRepository;
import com.wallet.payment.repository.WalletRepository;
import com.wallet.payment.service.WalletService;



@Service
public class WalletServiceImpl implements WalletService{
	
	private final UserRepository userRepository;
	private final WalletRepository walletRepository;
	private final LedgerEntryRepository ledgerEntryRepository;
	private final PaymentRepository paymentRepository;
	

	public WalletServiceImpl(UserRepository userRepository, 
			WalletRepository walletRepository,LedgerEntryRepository ledgerEntryRepository,PaymentRepository paymentRepository) {
		super();
		this.userRepository = userRepository;
		this.walletRepository = walletRepository;
		this.ledgerEntryRepository = ledgerEntryRepository;
		this.paymentRepository = paymentRepository;
	}

	@Override
	public WalletResponse createWallet(Long userId) {
		
		if(walletRepository.existsById(userId)) {
			throw new DuplicateWalletException("Wallet already exists for user: " + userId);
		}
		User user = userRepository.findById(userId)
				.orElseThrow(()-> new UserNotFoundException(userId));
		
		Wallet wallet = new Wallet(user,BigDecimal.ZERO,"INR",WalletStatus.ACTIVE);
		Wallet savedWallet = walletRepository.save(wallet);
				
		return mapToResponse(savedWallet);
	}

	@Override
	public WalletResponse getWalletByUserId(Long userId) {
		Wallet wallet = walletRepository.findByUserId(userId).
				orElseThrow(()->new WalletNotFoundException(userId));
		return mapToResponse(wallet);
	}
	
	private WalletResponse mapToResponse(Wallet wallet) {
		return new WalletResponse(wallet.getId(),wallet.getUser().getId(),
				wallet.getBalance(),wallet.getCurrency(),wallet.getStatus(),
				wallet.getCreatedAt(),wallet.getUpdatedAt());
	}

	@Transactional
	@Override
	public WalletResponse creditWallet(Long userId, BigDecimal amount) {
			Wallet wallet = walletRepository.findByUserIdForUpdate(userId)
					.orElseThrow(() -> new WalletNotFoundException(userId));
			if(wallet.getStatus() != WalletStatus.ACTIVE) {
				throw new WalletNotActiveException();
			}
			BigDecimal newBalance = wallet.getBalance().add(amount);
			wallet.setBalance(newBalance);
			Wallet savedWallet = walletRepository.save(wallet);
			
			
			LedgerEntry ledgerEntry = new 
					LedgerEntry(wallet,null,LedgerEntryType.CREDIT,amount,newBalance);
			
			ledgerEntryRepository.save(ledgerEntry);
		return mapToResponse(savedWallet);
	}

	@Transactional
	@Override
	public WalletResponse debitWallet(Long userId, BigDecimal amount) {
		
		Wallet wallet = walletRepository.findByUserIdForUpdate(userId)
						.orElseThrow(()-> new WalletNotFoundException(userId));
		
		if(wallet.getStatus() != WalletStatus.ACTIVE) {
			throw new WalletNotActiveException();
		}
		
		BigDecimal currentBalance = wallet.getBalance();
		
		if(currentBalance.compareTo(amount)<0) {
			throw new InsufficientBalanceException();
		}
		BigDecimal newBalance = currentBalance.subtract(amount);
		wallet.setBalance(newBalance);
		Wallet savedWallet = walletRepository.save(wallet);
		LedgerEntry ledgerEntry = new LedgerEntry(wallet,null,LedgerEntryType.DEBIT,amount,newBalance);
		ledgerEntryRepository.save(ledgerEntry);
		return mapToResponse(savedWallet);
		
	}
	
	@Transactional
	@Override
	public TransferResponse transfer(TransferRequest request, String idempotencyKey) {
		
		Payment existingPayment = paymentRepository.
				findByIdempotencyKey(idempotencyKey)
						.orElse(null);
		
		if(existingPayment != null) {
			
			Long existingSenderId =
		            existingPayment.getSenderWallet()
		                    .getUser()
		                    .getId();

		    Long existingReceiverId =
		            existingPayment.getReceiverWallet()
		                    .getUser()
		                    .getId();

		    boolean sameRequest =
		            existingSenderId.equals(request.getSenderUserId())
		            && existingReceiverId.equals(request.getReceiverUserId())
		            && existingPayment.getAmount()
		                    .compareTo(request.getAmount()) == 0;

		    if (!sameRequest) {
		        throw new IdempotencyConflictException();
		    }
			
			return new TransferResponse(existingPayment.getReference(),
					existingPayment.getStatus().name(),
					existingPayment.getSenderWallet().getUser().getId(),
					existingPayment.getReceiverWallet().getUser().getId(),
					existingPayment.getAmount(),
					"Request already Processed",
					true);
		}
			
		
		String reference = "PAY-"+UUID.randomUUID();

	    Long senderUserId = request.getSenderUserId();
	    Long receiverUserId = request.getReceiverUserId();
	    BigDecimal amount = request.getAmount();

	    if (senderUserId.equals(receiverUserId)) {
	        throw new InvalidTransferException(
	                "Sender and receiver cannot be the same");
	    }
	    /*Circular Wait - Dead Lock*/
	   /* Wallet senderWallet = walletRepository
	            .findByUserIdForUpdate(senderUserId)
	            .orElseThrow(() ->
	                    new WalletNotFoundException(senderUserId));

	    Wallet receiverWallet = walletRepository
	            .findByUserIdForUpdate(receiverUserId)
	            .orElseThrow(() ->
	                    new WalletNotFoundException(receiverUserId));*/
	    
	    
	    
	    List<Long> userIds = List.of(senderUserId,receiverUserId);
	    
	    List<Wallet> wallets = walletRepository.findByUserIdsForUpdate(userIds);
	    
	    Wallet senderWallet = wallets.stream()
	    		.filter(wallet->wallet.getUser().getId().equals(senderUserId))
	    		.findFirst()
	    		.orElseThrow(()->new WalletNotFoundException(senderUserId));

	    if (senderWallet.getStatus() != WalletStatus.ACTIVE) {
	        throw new WalletNotActiveException();
	    }
	    
	    Wallet receiverWallet = wallets.stream()
	    		.filter(wallet->wallet.getUser().getId().equals(receiverUserId))
	    		.findFirst()
	    		.orElseThrow(()->new WalletNotFoundException(receiverUserId));

	    if (receiverWallet.getStatus() != WalletStatus.ACTIVE) {
	        throw new WalletNotActiveException();
	    }

	    if (senderWallet.getBalance().compareTo(amount) < 0) {
	        throw new InsufficientBalanceException();
	    }
	    Payment payment = new Payment(
	            reference,
	            idempotencyKey,
	            senderWallet,
	            receiverWallet,
	            amount,
	            PaymentStatus.SUCCESS
	    );

	    Payment savedPayment =
	            paymentRepository.save(payment);

	    BigDecimal senderNewBalance =
	            senderWallet.getBalance().subtract(amount);

	    BigDecimal receiverNewBalance =
	            receiverWallet.getBalance().add(amount);

	    senderWallet.setBalance(senderNewBalance);
	    receiverWallet.setBalance(receiverNewBalance);

	    walletRepository.save(senderWallet);
	    walletRepository.save(receiverWallet);

	    LedgerEntry debitEntry = new LedgerEntry(
	            senderWallet,
	            savedPayment,
	            LedgerEntryType.DEBIT,
	            amount,
	            senderNewBalance
	    );

	    LedgerEntry creditEntry = new LedgerEntry(
	            receiverWallet,
	            savedPayment,
	            LedgerEntryType.CREDIT,
	            amount,
	            receiverNewBalance
	    );

	    ledgerEntryRepository.save(debitEntry);
	    ledgerEntryRepository.save(creditEntry);

	    return new TransferResponse(
	    		savedPayment.getReference(),
	            savedPayment.getStatus().name(),
	            senderUserId,
	            receiverUserId,
	            amount,
	            "Transfer Successful",
	            false
	    );
	}

}
