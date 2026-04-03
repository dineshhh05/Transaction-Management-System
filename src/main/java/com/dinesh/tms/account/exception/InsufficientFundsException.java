package com.dinesh.tms.account.exception;

public class InsufficientFundsException extends IllegalArgumentException{
    
    public InsufficientFundsException(){
        super("Insufficient Funds");
    }
}
