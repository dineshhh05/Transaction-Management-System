package com.dinesh.tms.account.exception;

import java.util.UUID;

public class ConcurrentAccountAccessException extends RuntimeException{
    public ConcurrentAccountAccessException(UUID accountId, Throwable cause){
        super("Conncurret transaction conflict for account: " + accountId, cause);
    }
}
