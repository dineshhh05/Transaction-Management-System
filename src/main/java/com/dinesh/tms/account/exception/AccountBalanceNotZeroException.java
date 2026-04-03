package com.dinesh.tms.account.exception;

public class AccountBalanceNotZeroException extends IllegalStateException{

    public AccountBalanceNotZeroException(){
        super("Account cannot be closed unless balance is zero.");
    }
    
}