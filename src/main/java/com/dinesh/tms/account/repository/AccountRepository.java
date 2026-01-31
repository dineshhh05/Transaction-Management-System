package com.dinesh.tms.account.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dinesh.tms.account.model.Account;

public interface AccountRepository extends JpaRepository<Account, UUID>{
    Optional<Account> findByOwnerUsername(String username);
}
