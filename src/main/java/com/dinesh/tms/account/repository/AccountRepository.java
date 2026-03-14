package com.dinesh.tms.account.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dinesh.tms.account.model.Account;
import com.dinesh.tms.account.model.AccountType;
import com.dinesh.tms.user.model.User;

public interface AccountRepository extends JpaRepository<Account, UUID>{
    
    List<Account> findByOwnerUsername(String username);

    List<Account> findAllByOwnerId(UUID userId); 

    boolean existsByOwnerAndAccountType(User owner, AccountType accountType);
}
