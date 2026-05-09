package com.bank.BankingApplication.Service;

import com.bank.BankingApplication.DTO.TransactionDto;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    TransactionDto createTransaction(TransactionDto transactionDto);

    TransactionDto getTransactionById(Long id);

    List<TransactionDto> getTransactionsByAccountId(Long accountId);

    List<TransactionDto> getTransactionsByUserId(Long userId);

    List<TransactionDto> getTransactionsByAccountAndDateRange(Long accountId, LocalDateTime startDate, LocalDateTime endDate);

    TransactionDto deposit(Long accountId, double amount, String description);

    TransactionDto withdraw(Long accountId, double amount, String description);

    TransactionDto transfer(Long fromAccountId, Long toAccountId, double amount, String description);

    List<TransactionDto> getTransactionHistory(Long accountId);
}