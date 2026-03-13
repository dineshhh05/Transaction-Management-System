package com.dinesh.tms.account.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinesh.tms.account.dto.AccountResponse;
import com.dinesh.tms.account.dto.ApplyCreditRequest;
import com.dinesh.tms.account.dto.ApplyDebitRequest;
import com.dinesh.tms.account.dto.CreateAccountRequest;
import com.dinesh.tms.account.dto.UpdateAccountStatusRequest;
import com.dinesh.tms.account.model.Account;
import com.dinesh.tms.account.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {
    
    private final AccountService accountService;


    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    // ---------- POST METHODS ----------

    // Create Account
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest req){

        Account newAccount = accountService.createAccount(req);

        AccountResponse res =  AccountResponse.from(newAccount);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(res);
    }



    // ---------- GET METHODS ----------
    
    // Explore pagination
    @GetMapping("/user/{ownerId}")
    public ResponseEntity<List<AccountResponse>> getAccountsByUserId(@PathVariable("ownerId") UUID ownerId){

        List<Account> listOfAccounts = accountService.getAccountsByUserID(ownerId);

        List<AccountResponse> listOfAccountResponse = new ArrayList<>();

        for(Account account : listOfAccounts){
            listOfAccountResponse.add(AccountResponse.from(account));
        }

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(listOfAccountResponse);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable("accountId") UUID accountId){

        Account account = accountService.getAccount(accountId);

        AccountResponse res = AccountResponse.from(account);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(res);
    }


    // ---------- PUT METHODS ----------
    @PutMapping("/{accountId}/credit")
    public ResponseEntity<AccountResponse> applyCredit(@PathVariable("accountId") UUID accountId, @RequestBody @Valid ApplyCreditRequest req){

        Account account = accountService.applyCredit(accountId, req.getCreditAmount());

        AccountResponse res = AccountResponse.from(account);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(res);
    }

    @PutMapping("/{accountId}/debit")
    public ResponseEntity<AccountResponse> debitCredit(@PathVariable("accountId") UUID accountId, @RequestBody @Valid ApplyDebitRequest req){

        Account account = accountService.applyDebit(accountId, req.getDebitAmount());

        AccountResponse res = AccountResponse.from(account);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(res);
    }


    // ---------- PATCH METHODS ----------

    @PatchMapping("/{accountId}")
    public ResponseEntity<AccountResponse> updateAccountStatus(@PathVariable("accountId") UUID accountId, @RequestBody @Valid UpdateAccountStatusRequest req){

        Account account = accountService.updateAccountStatus(accountId, req.getNewStatus());

        AccountResponse accountResponse = AccountResponse.from(account);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(accountResponse);
    }  


    // ---------- DELETE METHODS ----------
    @DeleteMapping("/{id}")
    public ResponseEntity<AccountResponse> closeAccount(@PathVariable("id") UUID id){

        Account account = accountService.deleteAccountByID(id);

        AccountResponse res = AccountResponse.from(account);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(res);
    }
    



}
