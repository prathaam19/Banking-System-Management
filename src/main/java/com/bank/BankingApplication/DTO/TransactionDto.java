package com.bank.BankingApplication.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private Long id;
    private String transactionType;
    private double amount;
    private String transactionStatus;
    private String description;
    private LocalDateTime transactionDate;
    private String referenceNumber;
    private Long fromAccountId;
    private String fromAccountNumber;
    private String fromAccountHolderName;
    private Long toAccountId;
    private String toAccountNumber;
    private String toAccountHolderName;
}