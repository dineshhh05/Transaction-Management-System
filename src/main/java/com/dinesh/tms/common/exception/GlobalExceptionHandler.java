package com.dinesh.tms.common.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dinesh.tms.account.exception.AccountNotFoundException;
import com.dinesh.tms.common.dto.ApiError;
import com.dinesh.tms.user.exception.DuplicateEmailException;
import com.dinesh.tms.user.exception.DuplicateUsernameException;
import com.dinesh.tms.user.exception.UnderageException;
import com.dinesh.tms.user.exception.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .collect(Collectors.joining(", "));

        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), "VALIDATION_ERROR", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }



    /* =====================
    DOMAIN EXCEPTIONS
    ===================== */

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ApiError> handleAccountNotFound(AccountNotFoundException ex){
        
        ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), "ACCOUNT_NOT_FOUND", ex.getMessage());
       
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException ex){
        
        ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), "USER_NOT_FOUND", ex.getMessage());
       
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(error);
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<ApiError> handleDuplicateUsernameException(DuplicateUsernameException ex){

        ApiError error = new ApiError(HttpStatus.CONFLICT.value(), "DUPLICATE_USERNAME", ex.getMessage());

        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(error);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ApiError> handleDuplicateEmailException(DuplicateEmailException ex){

        ApiError error = new ApiError(HttpStatus.CONFLICT.value(), "DUPLICATE_EMAIL", ex.getMessage());

        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(error);
    }

    @ExceptionHandler(UnderageException.class)
    public ResponseEntity<ApiError> handleUnderageException(UnderageException ex){

        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), "UNDERAGE_USER", ex.getMessage());

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(error);
    }










    //Unexpected error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex){
        ApiError error = new ApiError(500, "INTERNAL_ERROR", "Something went wrong");
      
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(error);
    }
}
