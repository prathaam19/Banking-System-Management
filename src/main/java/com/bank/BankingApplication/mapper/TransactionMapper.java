package com.bank.BankingApplication.mapper;

import com.bank.BankingApplication.DTO.TransactionDto;
import com.bank.BankingApplication.Entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionDto toDto(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setTransactionType(transaction.getTransactionType());
        dto.setAmount(transaction.getAmount());
        dto.setTransactionStatus(transaction.getTransactionStatus());
        dto.setDescription(transaction.getDescription());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setReferenceNumber(transaction.getReferenceNumber());

        // Map from account information
        if (transaction.getFromAccount() != null) {
            dto.setFromAccountId(transaction.getFromAccount().getId());
            dto.setFromAccountNumber(transaction.getFromAccount().getAccountNumber());
            dto.setFromAccountHolderName(transaction.getFromAccount().getAccountHolderName());
        }

        // Map to account information
        if (transaction.getToAccount() != null) {
            dto.setToAccountId(transaction.getToAccount().getId());
            dto.setToAccountNumber(transaction.getToAccount().getAccountNumber());
            dto.setToAccountHolderName(transaction.getToAccount().getAccountHolderName());
        }

        return dto;
    }

    public Transaction toEntity(TransactionDto dto) {
        if (dto == null) {
            return null;
        }

        Transaction transaction = new Transaction();
        transaction.setId(dto.getId());
        transaction.setTransactionType(dto.getTransactionType());
        transaction.setAmount(dto.getAmount());
        transaction.setTransactionStatus(dto.getTransactionStatus());
        transaction.setDescription(dto.getDescription());
        transaction.setReferenceNumber(dto.getReferenceNumber());

        // Note: Account relationships should be set separately
        // as they require repository access

        return transaction;
    }

    public void updateEntityFromDto(TransactionDto dto, Transaction transaction) {
        if (dto == null || transaction == null) {
            return;
        }

        if (dto.getTransactionType() != null) {
            transaction.setTransactionType(dto.getTransactionType());
        }
        if (dto.getTransactionStatus() != null) {
            transaction.setTransactionStatus(dto.getTransactionStatus());
        }
        if (dto.getDescription() != null) {
            transaction.setDescription(dto.getDescription());
        }
    }
}