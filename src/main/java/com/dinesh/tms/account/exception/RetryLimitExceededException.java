package com.dinesh.tms.account.exception;

public class RetryLimitExceededException extends RuntimeException{
    public RetryLimitExceededException(String msg, Throwable e){
        super(msg, e);
    }
}
