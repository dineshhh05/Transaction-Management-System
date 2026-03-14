package com.dinesh.tms.account.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.dinesh.tms.account.model.Account;
import com.dinesh.tms.account.model.AccountCurrency;
import com.dinesh.tms.account.model.AccountStatus;
import com.dinesh.tms.account.model.AccountType;

// Response DTO, Read only
public class AccountResponse {
    
    private UUID id;
    private Long accountNumber;
    private AccountType accountType;
    private AccountCurrency currency;
    private AccountStatus status;
    private BigDecimal currentBalance;
    private Instant createdAt;


    public AccountResponse(
        UUID id,
        Long accountNumber,
        AccountType accountType,
        AccountCurrency currency,
        AccountStatus status,
        BigDecimal currentBalance,
        Instant createdAt
    ) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.currency = currency;
        this.status = status;
        this.currentBalance = currentBalance;
        this.createdAt = createdAt;
    }

    // DTO Mapper 
    public static AccountResponse from(Account account){
        return new AccountResponse(
            account.getId(), 
            account.getAccountNumber(),
            account.getAccountType(), 
            account.getAccountCurrency(), 
            account.getStatus(), 
            account.getCurrentBalance(), 
            account.getCreatedAt()
        );
    }
    
    
    // Getters
    public UUID getId() {
        return id;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }


    public AccountType getAccountType() {
        return accountType;
    }
    
    public AccountCurrency getCurrency() {
        return currency;
    }
    
    public AccountStatus getStatus() {
        return status;
    }
    
    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }
   
    public Instant getCreatedAt() {
        return createdAt;
    }

    
}
