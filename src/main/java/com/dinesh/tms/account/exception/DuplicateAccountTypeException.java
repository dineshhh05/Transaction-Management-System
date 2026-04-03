package com.dinesh.tms.account.exception;

import com.dinesh.tms.account.model.AccountType;

public class DuplicateAccountTypeException extends IllegalStateException{
    
    public DuplicateAccountTypeException(AccountType accountType){
        super("User is limited to only 1 " + accountType + " account");
    }
}
