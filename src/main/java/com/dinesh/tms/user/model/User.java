package com.dinesh.tms.user.model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.dinesh.tms.account.model.Account;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String username;
    private String emailID;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String postalCode;

    // User(1) <---> (M)Accounts
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)     
    private List<Account> accounts;

    private Boolean accountLocked;
    private Integer riskScore;      //change type to an apropriate one instead of Integer
    private Instant createdAt;

    // TODO:
    // private String hashedPassword;
    // private String failedLoginAttempts;


    public User(){}

    public User(
        String username,
        String emailID,

        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String postalCode
    ) {
        this.username = username;
        this.emailID = emailID;

        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.postalCode = postalCode;

        this.createdAt = Instant.now();

        this.accountLocked = false;
        this.riskScore = 0;
    }

    // Getters
    public UUID getId() {return id;}
    public String getUsername() {return username;}
    public String getEmailID() {return emailID;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public LocalDate getDateOfBirth() {return dateOfBirth;}
    public String getPostalCode() {return postalCode;}
    public List<Account> getAccounts() {return accounts;}
    public Boolean getAccountLocked() {return accountLocked;}
    public Integer getRiskScore() {return riskScore;}
    public Instant getCreatedAt() {return createdAt;}

    // Setters
    public void setAccountLocked(Boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

}
