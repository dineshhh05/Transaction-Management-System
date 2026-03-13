package com.dinesh.tms.account.exception;


public class AccessClosedAccountException extends IllegalStateException{
    
    public AccessClosedAccountException(){
        super("Illegal access to a closed account.");
    }
}
