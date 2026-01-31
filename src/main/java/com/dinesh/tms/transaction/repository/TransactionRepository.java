package com.dinesh.tms.transaction.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.TransactionStatus;

import com.dinesh.tms.transaction.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findBySender_Id(UUID senderId);

    List<Transaction> findByReceiver_Id(UUID receiverId);

    // Optional: fetch transactions by status if you need it later
    List<Transaction> findByStatus(TransactionStatus status);
}
