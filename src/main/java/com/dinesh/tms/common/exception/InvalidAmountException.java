package com.dinesh.tms.common.exception;

public class InvalidAmountException extends IllegalArgumentException{
    
    public InvalidAmountException(String msg){
        super("Invalid Amount:" + msg);
    }
}
