# Digital Wallet & Payment Ledger Platform

A backend application built with **Java, Spring Boot, Spring Data JPA, and MySQL** to simulate a digital wallet and payment system.

The project focuses on wallet management, financial transactions, ledger entries, transaction consistency, and reliable payment processing.

## Features

### User Management

- User creation
- User retrieval
- User-Wallet relationship

### Wallet Management

- Wallet creation
- Wallet retrieval
- Wallet balance management
- One wallet per user

### Ledger

- Ledger entry creation for wallet transactions
- Credit and debit transaction tracking
- Balance-after-transaction tracking

### Payment

- Sender and receiver wallet relationships
- Payment reference generation
- Payment status management
- Credit and debit operations
- Transactional wallet updates

### Transaction Safety

- Database transactions using `@Transactional`
- Pessimistic wallet locking for concurrent operations
- Sufficient balance validation
- Prevention of inconsistent wallet balances

### Idempotency

- Idempotency support for financial operations
- Duplicate request detection
- Prevention of duplicate credits/debits
- Idempotency key handling for request retries

### Exception Handling

- Custom business exceptions
- Centralized exception handling using `@RestControllerAdvice`
- Meaningful HTTP error responses

## Technology Stack

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- REST APIs
- Git / GitHub

## Project Structure

```text
src/main/java/com/wallet/payment/

├── user
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   └── service
│
├── wallet
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   └── service
│
├── ledger
│   ├── entity
│   ├── repository
│   └── service
│
├── payment
│   ├── entity
│   ├── repository
│   └── service
│
└── exception
    └── GlobalExceptionHandler
```

## Current Status

The following modules and concepts have been implemented:

- User module
- Wallet module
- User-Wallet relationship
- Wallet retrieval
- Ledger entity and transaction recording
- Credit/debit operations
- Payment entity and payment flow
- Transaction management
- Pessimistic locking
- Exception handling

### Currently Working On

**Idempotency**

The current focus is implementing idempotency for financial operations so that retrying the same request does not result in duplicate wallet transactions.

## Upcoming Work

- Complete idempotency implementation
- Complete payment/transfer flow
- Ledger history API
- Input validation and edge cases
- Authentication and authorization
- Unit and integration testing
- API documentation
- Dockerization
- Production-ready configuration
- Deployment

## Example Financial Flow

```text
User
  │
  ▼
Wallet
  │
  ├── Credit
  │     │
  │     ├── Update Balance
  │     └── Create CREDIT Ledger Entry
  │
  └── Debit / Payment
        │
        ├── Validate Balance
        ├── Lock Wallet
        ├── Update Balance
        └── Create DEBIT Ledger Entry
```

## Goal

The goal of this project is to build a production-oriented wallet and payment backend while demonstrating practical knowledge of:

- Spring Boot
- REST API design
- JPA/Hibernate
- Database relationships
- Transaction management
- Concurrency control
- Idempotency
- Exception handling
- Financial transaction modeling
- Clean backend architecture
