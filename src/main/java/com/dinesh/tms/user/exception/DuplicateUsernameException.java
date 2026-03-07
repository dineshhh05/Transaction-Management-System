package com.dinesh.tms.user.exception;

public class DuplicateUsernameException extends IllegalArgumentException {

    public DuplicateUsernameException (String username){
        super("Username: '" + username + "' already exists");
    }
}