package com.dinesh.tms.user.exception;

public class UnderageException extends IllegalArgumentException{
    
    public UnderageException() {
        super("Age must be atleast 18 years");
    }
}
