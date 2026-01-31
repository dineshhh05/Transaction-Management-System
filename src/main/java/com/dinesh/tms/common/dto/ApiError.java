package com.dinesh.tms.common.dto;

import java.time.Instant;

public class ApiError {

    private final int status;
    private final String error;
    private final String message;
    private final Instant timestamp;


    public ApiError(int status, String error, String msg){
        this.status = status;
        this.error = error;
        this.message = msg;
        this.timestamp = Instant.now();       
    }

    public int getStatus(){
        return this.status;
    }

    public String getError(){
        return this.error;
    }

    public String getMessage(){
        return this.message;
    }

    public Instant getTimestamp(){
        return this.timestamp;
    }
}
