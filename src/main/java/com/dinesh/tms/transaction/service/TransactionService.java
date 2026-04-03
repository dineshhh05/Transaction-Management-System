package com.dinesh.tms.transaction.service;

import org.springframework.stereotype.Service;

import com.dinesh.tms.account.repository.AccountRepository;
import com.dinesh.tms.transaction.repository.TransactionRepository;


@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;


    // Constructor injection
    public TransactionService(AccountRepository accountRepository, TransactionRepository transactionRepository){
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }
    
}
