package com.dinesh.tms.account.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dinesh.tms.account.dto.CreateAccountRequest;
import com.dinesh.tms.account.exception.AccountBalanceNotZeroException;
import com.dinesh.tms.account.exception.AccountNotFoundException;
import com.dinesh.tms.account.exception.DuplicateAccountTypeException;
import com.dinesh.tms.account.exception.RetryLimitExceededException;
import com.dinesh.tms.account.model.Account;
import com.dinesh.tms.account.model.AccountStatus;
import com.dinesh.tms.account.repository.AccountRepository;
import com.dinesh.tms.user.exception.UserNotFoundException;
import com.dinesh.tms.user.model.User;
import com.dinesh.tms.user.repository.UserRepository;

 
@Service
@Transactional(readOnly = true)
public class AccountService {
    
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionHelper transactionHelper;

    // Constructor injection
    public AccountService(AccountRepository accountRepository, UserRepository userRepository, TransactionHelper transactionHelper){
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionHelper = transactionHelper;
    }


    @Transactional
    public Account createAccount(CreateAccountRequest req){

        User owner = userRepository.findById(req.getOwnerId())
            .orElseThrow(() -> new UserNotFoundException(req.getOwnerId()));

        boolean exists = accountRepository.existsByOwnerAndAccountType(owner, req.getAccountType());

        if(exists) {
            throw new DuplicateAccountTypeException(req.getAccountType());
        }

        Account newAccount = new Account(req.getAccountType(), owner, req.getCurrency());

        try {
            return accountRepository.save(newAccount);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateAccountTypeException(req.getAccountType());
        }
        
    }

    public Account getAccount(UUID accountId){

        return accountRepository.findById(accountId)
            .orElseThrow(() -> new AccountNotFoundException(accountId));
    }



    public List<Account> getAccountsByUserID(UUID userID){

        if(userRepository.existsById(userID)){
            return accountRepository.findAllByOwnerId(userID);
        } else {
            throw new UserNotFoundException(userID);
        }
    }

    @Transactional
    public Account updateAccountStatus(UUID accountId, AccountStatus newStatus){

        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new AccountNotFoundException(accountId));

        account.ensureUpdateable();

        account.setStatus(newStatus);

        return accountRepository.save(account);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Account applyCredit(UUID accountId, BigDecimal creditAmount){

        int maxRetries = 3;

        for(int i=1; i<=maxRetries; i++){
            try {

                return transactionHelper.attemptCredit(accountId, creditAmount);

            } catch (ObjectOptimisticLockingFailureException e) {

                if (i == maxRetries) {
                    throw new RetryLimitExceededException("Transaction retry limit exceeded, try again later", e);
                }

                try {
                    Thread.sleep(10L * i);
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        throw new IllegalStateException("Unreachable");
    }

    

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Account applyDebit(UUID accountId, BigDecimal debitAmount){

        int maxRetries = 3;

        for(int i=1; i<=maxRetries; i++){
            try {

                return transactionHelper.attemptDebit(accountId, debitAmount);
                
            } catch (ObjectOptimisticLockingFailureException e) {

                if(i == maxRetries){
                    throw new RetryLimitExceededException("Transaction retry limit exceeded, try again later", e);
                }

                try {
                    Thread.sleep(10L * i);
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        throw new IllegalStateException("Unreachable");
    }

    

    @Transactional
    public Account closeAccountByID(UUID id){

        Account account = accountRepository.findById(id)
            .orElseThrow(() -> new AccountNotFoundException(id));

        account.ensureUpdateable();

        if (account.getCurrentBalance().compareTo(BigDecimal.ZERO) > 0 ) {
            throw new AccountBalanceNotZeroException();
        }

        account.setClosedAt(Instant.now());
        account.setStatus(AccountStatus.CLOSED);

        return accountRepository.save(account);
    }    
}
