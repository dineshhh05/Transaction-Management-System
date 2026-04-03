package com.dinesh.tms.account.exception;


public class AccountOperationNotAllowedException extends IllegalStateException{
    
    public AccountOperationNotAllowedException(){
        super("Operation not allowed on this account.");
    }
}
