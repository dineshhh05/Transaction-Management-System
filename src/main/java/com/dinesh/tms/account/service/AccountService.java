package com.dinesh.tms.account.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dinesh.tms.account.exception.AccountNotFoundException;
import com.dinesh.tms.account.model.Account;
import com.dinesh.tms.account.repository.AccountRepository;
import com.dinesh.tms.user.exception.UserNotFoundException;
import com.dinesh.tms.user.model.User;
import com.dinesh.tms.user.repository.UserRepository;

 
@Service
@Transactional(readOnly = true)
public class AccountService {
    
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    // Constructor injection
    public AccountService(AccountRepository accountRepository, UserRepository userRepository){
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }


    // Method only for testing purpose
    // public Account addAccount(AccountType accountType, User owner){

    //     Account newAccount = new Account(accountType, owner);

    //     return accountRepository.save(newAccount);
    // }

    public List<Account> getAccountsByUserID(UUID userID){

        if(userRepository.existsById(userID)){
            return accountRepository.findAllByOwnerId(userID);
        } else {
            throw new UserNotFoundException(userID);
        }
    }


    @Transactional
    public void deleteAccountByID(UUID id){

        Account Account = accountRepository.findById(id)
            .orElseThrow(() -> new AccountNotFoundException(id));

        accountRepository.delete(Account);
    }    
}
