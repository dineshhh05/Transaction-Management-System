package com.dinesh.tms.account.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dinesh.tms.account.dto.CreateAccountRequest;
import com.dinesh.tms.account.exception.AccessClosedAccountException;
import com.dinesh.tms.account.exception.AccountNotFoundException;
import com.dinesh.tms.account.exception.DuplicateAccountTypeException;
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

    // Constructor injection
    public AccountService(AccountRepository accountRepository, UserRepository userRepository){
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
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

        if(account.getStatus() == AccountStatus.CLOSED){
            throw new AccessClosedAccountException();
        }

        account.setStatus(newStatus);

        return accountRepository.save(account);
    }

    @Transactional
    public Account applyCredit(UUID accountId, BigDecimal creditAmount){

        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new AccountNotFoundException(accountId));

        if( account.getStatus() == AccountStatus.CLOSED ||
           account.getStatus() == AccountStatus.FROZEN ||
           account.getStatus() == AccountStatus.SUSPENDED
        ){
            throw new AccessClosedAccountException();
        }

        account.applyCredit(creditAmount);

        return accountRepository.save(account);
    }

    @Transactional
    public Account applyDebit(UUID accountId, BigDecimal debitAmount){

        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new AccountNotFoundException(accountId));

        if( account.getStatus() == AccountStatus.CLOSED ||
            account.getStatus() == AccountStatus.FROZEN ||
            account.getStatus() == AccountStatus.SUSPENDED
        ){
            throw new AccessClosedAccountException();
        }

        account.applyDebit(debitAmount);

        return accountRepository.save(account);
    }


    @Transactional
    public Account deleteAccountByID(UUID id){

        Account account = accountRepository.findById(id)
            .orElseThrow(() -> new AccountNotFoundException(id));

        account.setClosedAt(Instant.now());
        account.setStatus(AccountStatus.CLOSED);

        return accountRepository.save(account);
    }    
}
