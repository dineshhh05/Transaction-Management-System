package com.dinesh.tms.account.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.UUID;

import com.dinesh.tms.user.model.User;

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
import jakarta.persistence.Version;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    // Account(M) <----> (1)User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Enumerated(EnumType.STRING)
    private AccountCurrency currency;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Column(precision = 19, scale = 2)
    private BigDecimal currentBalance;

    @Version
    private Long version;
    private Instant createdAt;
    private Instant closedAt;


    // No-args constructor
    protected Account(){}

    public Account(AccountType accountType, User owner) {
        this.accountType = accountType;
        this.owner = owner;
        this.currency = AccountCurrency.CAD;
        this.status = AccountStatus.ACTIVE;
        this.currentBalance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
        this.createdAt = Instant.now();
    }

    public void applyCredit(BigDecimal amount){
        amount = validateAmount(amount);
        this.currentBalance = currentBalance.add(amount);
    }

    public void applyDebit(BigDecimal amount){
        amount = validateAmount(amount);
        if(this.currentBalance.compareTo(amount) < 0){
            throw new IllegalArgumentException("Insufficient funds");
        }

        this.currentBalance = currentBalance.subtract(amount);
    }

    // Getters 
    public UUID getID() {return id;}
    public User getOwner() {return owner;}
    public AccountType getAccountType() {return accountType;}
    public AccountCurrency getAccountCurrency() {return currency;}
    public BigDecimal getCurrentBalance() {return currentBalance;}
    public Instant getCreatedAt() {return createdAt;}
    public Instant getClosedAt() {return closedAt;}
    public AccountStatus getStatus() {return status;}
    public Long getVersion() {return version;}

    // Setters
    public void setStatus(AccountStatus status) {this.status = status;}
    public void setClosedAt(Instant closedAt) {this.closedAt = closedAt;}



    // Internal validation helper
    // TODO: Custom exceptions needed here
    private BigDecimal validateAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        // ensure 2 decimal places
        return amount.setScale(2, RoundingMode.HALF_EVEN);
    }


    
}
