package com.dinesh.tms.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dinesh.tms.account.exception.AccountNotFoundException;
import com.dinesh.tms.common.dto.ApiError;

@ControllerAdvice
public class GlobalExceptionHandler {
      /* =====================
       DOMAIN EXCEPTIONS
       ===================== */

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ApiError> handleAccountNotFound(AccountNotFoundException ex){
        
        ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), "ACCOUNT_NOT_FOUND", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
