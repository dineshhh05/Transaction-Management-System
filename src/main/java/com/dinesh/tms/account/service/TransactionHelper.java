package com.dinesh.tms.account.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dinesh.tms.account.exception.AccountNotFoundException;
import com.dinesh.tms.account.model.Account;
import com.dinesh.tms.account.repository.AccountRepository;


@Service
public class TransactionHelper {
     private final AccountRepository accountRepository;

    // Constructor injection
    public TransactionHelper(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public Account attemptCredit(UUID accountId, BigDecimal creditAmount) {
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new AccountNotFoundException(accountId));

        account.ensureTransactionAllowed();
        account.applyCredit(creditAmount);

        return accountRepository.save(account);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Account attemptDebit(UUID accountId, BigDecimal debitAmount) {
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new AccountNotFoundException(accountId));

        account.ensureTransactionAllowed();
        account.applyDebit(debitAmount);

        return accountRepository.save(account);
    }

}
