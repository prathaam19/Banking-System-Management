package com.bank.BankingApplication.Entity;

import com.bank.BankingApplication.constants.AppConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Account holder name is required")
    @Column(name = "account_holder_name", nullable = false)
    private String accountHolderName;

    @NotBlank(message = "Account number is required")
    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;

    @NotBlank(message = "Account type is required")
    @Column(name = "account_type", nullable = false)
    private String accountType = AppConstants.SAVINGS_ACCOUNT;

    @PositiveOrZero(message = "Balance cannot be negative")
    @Column(nullable = false)
    private double balance = 0.0;

    @NotBlank(message = "Account status is required")
    @Column(name = "account_status", nullable = false)
    private String accountStatus = AppConstants.ACCOUNT_ACTIVE;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relationships
    @NotNull(message = "User is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> outgoingTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> incomingTransactions = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Helper methods
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && this.balance >= amount) {
            this.balance -= amount;
        }
    }

    public boolean hasSufficientBalance(double amount) {
        return this.balance >= amount;
    }

    public boolean isActive() {
        return AppConstants.ACCOUNT_ACTIVE.equals(this.accountStatus);
    }
}
