package com.dinesh.tms.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String firstName;
    private String lastName;
    private String username;
    private String emailID;
    private Integer creditScore;
    private Instant createdAt;
    private BigDecimal balance;

    // No-args constructor
    public Account(){
        this.createdAt = Instant.now();
    }

    public Account(String firstName, String lastName, String username, String emailId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.emailID = emailId;
    }

    // Getters 
    public UUID getAccountID() {return id;}
    public String getFirstName() {return firstName;} 
    public String getLastName() {return lastName;}
    public String getUsername() {return username;}
    public String getEmailID() {return emailID;}
    public Integer getCreditScore() {return creditScore;}
    public Instant getCreatedAt() {return createdAt;}
    public BigDecimal getBalance() {return balance;}

    // No setters to enforce immutability
    
}
