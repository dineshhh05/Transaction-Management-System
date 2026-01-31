package com.dinesh.tms.transaction.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.dinesh.tms.account.model.Account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // Each transaction has one sender and one reciver but each sender and reciver can have multiple transactions
    // transaction (M) <-----> (1) sender
    // transaction (M) <-----> (1) reciver
    // Lazy fetch to avoid loading full account data when not needed
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Account sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Account receiver;


    @Column(precision = 19, scale = 2)
    private BigDecimal amount;

    private Instant initiatedDate;
    private Instant completionDate;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private String description;


    // No-args constructor 
    public Transaction(){}

    public Transaction(Account sender, 
        Account receiver, 
        BigDecimal amount, 
        Instant initiatedDate, 
        Instant completionDate, 
        TransactionStatus status, 
        String description){

            this.receiver = receiver;
            this.sender = sender;
            this.amount = amount;
            this.initiatedDate = initiatedDate;
            this.completionDate = completionDate;
            this.status = status;
            this.description = description;
    }


    // Getters
    public UUID getTransactionID() {return id;}
    public Account getSender() {return sender;}
    public Account getReciver() {return receiver;}
    public BigDecimal getAmount() {return amount;}
    public Instant getInitiatedDate() {return initiatedDate;}
    public Instant getCompletionDate() {return completionDate;}
    public TransactionStatus getTransactionStatus() {return status;}
    public String getDescription() {return description;}

    // Setters



}