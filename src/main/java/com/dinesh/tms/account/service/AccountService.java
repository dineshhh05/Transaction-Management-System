package com.dinesh.tms.account.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dinesh.tms.account.exception.AccountNotFoundException;
import com.dinesh.tms.account.model.Account;
import com.dinesh.tms.account.repository.AccountRepository;
 
@Service
public class AccountService {
    
    private final AccountRepository accountRepository;

    // Constructor injection
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }


    // Method only for testing purpose
    // public Account addAccount(String firstName, String lastName, String username, String emailId){

    //     // Account newAccount = new Account(firstName, lastName, username, emailId);

    //     // return accountRepository.save(newAccount);
    // }


    @Transactional
    public void deleteAccountByID(UUID id){

        Account Account = accountRepository.findById(id)
            .orElseThrow(() -> new AccountNotFoundException(id));

        accountRepository.delete(Account);
    }    
}
