package com.dinesh.tms.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

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
    // LAZY fetch to avoid loading full account data when not needed
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Account sender;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Account receiver;


    @Column(precision = 19, scale = 4)
    private BigDecimal amount;

    private Instant initiatedDate;
    private Instant completionDate;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private String description;


    // No-args constructor 
    public Transaction(){}


    // Getters
    public UUID getTransactionID() {return id;}
    public Account getSender() {return sender;}
    public Account getReciver() {return receiver;}
    public BigDecimal getAmount() {return amount;}
    public Instant getInitiatedDate() {return initiatedDate;}
    public Instant getCompletionDate() {return completionDate;}
    public TransactionStatus getTransactionStatus() {return status;}
    public String getDescription() {return description;}

    // No setters to enforce immutability



}