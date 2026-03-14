package com.dinesh.tms.account.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.dinesh.tms.account.exception.AccountOperationNotAllowedException;
import com.dinesh.tms.account.exception.InsufficientFundsException;
import com.dinesh.tms.common.exception.InvalidAmountException;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;


@Entity
@Table(
    name = "accounts",
    uniqueConstraints = @UniqueConstraint(columnNames = {"owner_id", "account_type"})
)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private Long accountNumber;

    // Account(M) <----> (1)Owner (User)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountCurrency currency;

    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal currentBalance;


    @Column(nullable = false)
    private Instant createdAt;        

    @Column(nullable = false)
    private Instant updatedAt;

    private Instant closedAt;

    @Version
    private Long version;
    


    // No-args constructor
    protected Account(){}

    // Example Account
    //  id              =       1h1h2h-2323h4-434h34ks-23h-3xhu4435h-sd292h3
    //  accountNumber   =       64389 77830
    //  owner_id        =       j43928-t34872-54v345-23654363h-65vert345-her
    //  accountType     =       SAVINGS
    //  status          =       ACTIVE
    //  currency        =       CAD
    //  currentBalance  =       7839.44
    //  version         =       1
    //  createdAt       =       1982-08-30
    //  updatedAt       =       2026-09-20

    public Account(AccountType accountType, User owner, AccountCurrency currency) {
        this.accountNumber = ThreadLocalRandom.current().nextLong(1000000000L, 9999999999L);
        this.owner = owner;
        this.accountType = accountType;
        this.status = AccountStatus.ACTIVE;
        this.currency = currency;
        this.currentBalance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
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

    public void applyCredit(BigDecimal amount){
        amount = validateAmount(amount);
        this.currentBalance = currentBalance.add(amount);
    }

    public void applyDebit(BigDecimal amount){
        amount = validateAmount(amount);
       
        if(this.currentBalance.compareTo(amount) < 0){
            throw new InsufficientFundsException();
        }

        this.currentBalance = currentBalance.subtract(amount);
    }

    public void ensureTransactionAllowed(){
        if( this.status == AccountStatus.CLOSED || 
            this.status == AccountStatus.FROZEN ||
            this.status == AccountStatus.SUSPENDED
        ) {
            throw new AccountOperationNotAllowedException();
        }
    }

    public void ensureUpdateable(){
        if( this.status == AccountStatus.CLOSED) {
            throw new AccountOperationNotAllowedException();
        }
    }

    // Getters 
    public UUID getId() {return id;}
    public User getOwner() {return owner;}
    public AccountType getAccountType() {return accountType;}
    public AccountCurrency getAccountCurrency() {return currency;}
    public BigDecimal getCurrentBalance() {return currentBalance;}
    public Instant getCreatedAt() {return createdAt;}
    public Instant getClosedAt() {return closedAt;}
    public AccountStatus getStatus() {return status;}
    public Long getVersion() {return version;}
    public Long getAccountNumber() {return accountNumber;}
    public Instant getUpdatedAt() {return updatedAt;}



    // Setters
    public void setStatus(AccountStatus status) {this.status = status;}
    public void setClosedAt(Instant closedAt) {this.closedAt = closedAt;}



    // Internal validation helper
    private BigDecimal validateAmount(BigDecimal amount) {
        
        if (amount == null) {
            throw new InvalidAmountException("Amount cannot be null");
        }
        if (amount.signum() <= 0) {
            throw new InvalidAmountException("Amount must be positive");
        }

        // ensure 2 decimal places
        return amount.setScale(2, RoundingMode.HALF_EVEN);
    }

}
