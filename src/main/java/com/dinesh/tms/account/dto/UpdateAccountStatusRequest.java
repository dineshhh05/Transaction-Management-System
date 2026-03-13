package com.dinesh.tms.account.dto;

import com.dinesh.tms.account.model.AccountStatus;

import jakarta.validation.constraints.NotNull;

public class UpdateAccountStatusRequest {
    
    @NotNull
    AccountStatus newStatus;

    public AccountStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(AccountStatus newStatus) {
        this.newStatus = newStatus;
    }
}
