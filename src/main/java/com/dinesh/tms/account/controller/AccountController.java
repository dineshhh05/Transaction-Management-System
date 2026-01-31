package com.dinesh.tms.account.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinesh.tms.account.model.Account;
import com.dinesh.tms.account.repository.AccountRepository;
import com.dinesh.tms.account.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
    
    private final AccountService accountService;
    // private final AccountRepository accountRepository;

    @Autowired
    public AccountController(AccountService accountService, AccountRepository accountRepository){
        this.accountService = accountService;
        // this.accountRepository = accountRepository;
    }

    // @PostMapping("/create")
    // public Account createAccount(@RequestBody String firstname, String lastname, String username, String emailID){
    //     return accountService.addAccount(firstname, lastname, username, emailID);
    // };


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") UUID id){

        accountService.deleteAccountByID(id);
        
        return ResponseEntity.noContent().build();
    }
}
