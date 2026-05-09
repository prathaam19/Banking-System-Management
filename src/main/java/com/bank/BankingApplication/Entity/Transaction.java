package com.bank.BankingApplication.Entity;

import com.bank.BankingApplication.constants.AppConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Transaction type is required")
    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    @Positive(message = "Amount must be positive")
    @Column(nullable = false)
    private double amount;

    @NotBlank(message = "Transaction status is required")
    @Column(name = "transaction_status", nullable = false)
    private String transactionStatus = AppConstants.PENDING;

    @Column(name = "description")
    private String description;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "reference_number", unique = true)
    private String referenceNumber;

    // Relationships
    @NotNull(message = "From account is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id", nullable = false)
    private Account fromAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_id")
    private Account toAccount;

    @PrePersist
    protected void onCreate() {
        transactionDate = LocalDateTime.now();
        if (referenceNumber == null) {
            referenceNumber = generateReferenceNumber();
        }
    }

    // Helper methods
    private String generateReferenceNumber() {
        return "TXN" + System.currentTimeMillis() + String.format("%04d", id != null ? id : 0);
    }

    public boolean isSuccessful() {
        return AppConstants.SUCCESS.equals(this.transactionStatus);
    }

    public boolean isTransfer() {
        return AppConstants.TRANSFER.equals(this.transactionType);
    }

    public boolean isDeposit() {
        return AppConstants.DEPOSIT.equals(this.transactionType);
    }

    public boolean isWithdrawal() {
        return AppConstants.WITHDRAWAL.equals(this.transactionType);
    }
}