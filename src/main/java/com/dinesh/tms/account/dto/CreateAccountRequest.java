package com.dinesh.tms.account.dto;

import java.util.UUID;

import com.dinesh.tms.account.model.AccountCurrency;
import com.dinesh.tms.account.model.AccountType;
import jakarta.validation.constraints.NotNull;

public class CreateAccountRequest {
    
    @NotNull(message = "Account type required")
    private AccountType accountType;

    @NotNull(message = "ownerId cannot be null")
    private UUID ownerId;

    @NotNull(message = "Account currency required")
    private AccountCurrency currency;

    protected CreateAccountRequest() {}

    public CreateAccountRequest(
        AccountType accountType,
        UUID ownerId,
        AccountCurrency currency
    ){
        this.accountType = accountType;
        this.ownerId = ownerId;
        this.currency = currency;
    }

    
    // Getters and setters
    public AccountType getAccountType() {
        return accountType;
    }


    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public AccountCurrency getCurrency() {
        return currency;
    }


    public void setCurrency(AccountCurrency currency) {
        this.currency = currency;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }




    


}
