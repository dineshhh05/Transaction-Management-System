package com.dinesh.tms.user.model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.dinesh.tms.account.model.Account;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable=false, unique=true, length=50)
    private String username;

    @Column(nullable=false, unique=true, length=255)
    private String email;

    @Column(nullable=false, length=50)
    private String firstName;

    @Column(nullable=false, length=50)
    private String lastName;

    @Column(nullable=false)
    private LocalDate dateOfBirth;

    // Postal code that matches canadian postal codes (A1A1A1)
    @Column(nullable=false, length=6)
    private String postalCode;

    // User(1) <---> (M)Accounts
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)     
    private List<Account> accounts;

    @Column(nullable=false)
    private Boolean accountLocked;

    @Column(nullable=false)
    private Integer riskScore;

    @Column(nullable=false)
    private Instant createdAt;

    @Column(nullable=false)
    private Instant updatedAt;


    public User(){}

    public User(
        String username,
        String email,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String postalCode
    ) {
        this.username = username;
        this.email = email;

        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.postalCode = postalCode;

        this.accountLocked = false;
        this.riskScore = 0;
    }

    @PrePersist
    public void onCreate(){
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    public void onUpdate(){
        this.updatedAt = Instant.now();
    }

    // Getters
    public UUID getId() {return id;}
    public String getUsername() {return username;}
    public String getEmail() {return email;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public LocalDate getDateOfBirth() {return dateOfBirth;}
    public String getPostalCode() {return postalCode;}
    
    public List<Account> getAccounts() {
        return accounts != null ? List.copyOf(accounts) : List.of();
    }

    public Boolean getAccountLocked() {return accountLocked;}
    public Integer getRiskScore() {return riskScore;}
    public Instant getCreatedAt() {return createdAt;}
    public Instant getUpdatedAt() {return updatedAt;}
    
    // Setters
    public void setAccountLocked(Boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

}
