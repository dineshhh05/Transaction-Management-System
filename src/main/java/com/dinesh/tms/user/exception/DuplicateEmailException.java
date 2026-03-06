package com.dinesh.tms.user.exception;

public class DuplicateEmailException extends IllegalArgumentException{
    
    public DuplicateEmailException(String email){
        super("Email: '" + email + "' already exists");
    }
}
