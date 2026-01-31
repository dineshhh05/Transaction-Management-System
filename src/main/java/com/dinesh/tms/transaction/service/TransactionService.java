package com.dinesh.tms.transaction.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dinesh.tms.account.model.Account;
import com.dinesh.tms.account.repository.AccountRepository;
import com.dinesh.tms.transaction.model.Transaction;
import com.dinesh.tms.transaction.model.TransactionStatus;
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

    // public Transaction addTransaction(UUID senderID, UUID receiverID, BigDecimal amount, String description){}
    
}
