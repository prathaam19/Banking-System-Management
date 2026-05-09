package com.bank.BankingApplication.Service;

import com.bank.BankingApplication.DTO.TransactionDto;
import com.bank.BankingApplication.Entity.Account;
import com.bank.BankingApplication.Entity.Transaction;
import com.bank.BankingApplication.Exception.AccountNotFoundException;
import com.bank.BankingApplication.Repository.AccountRepository;
import com.bank.BankingApplication.Repository.TransactionRepository;
import com.bank.BankingApplication.constants.AppConstants;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @Override
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        Transaction transaction = modelMapper.map(transactionDto, Transaction.class);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return modelMapper.map(savedTransaction, TransactionDto.class);
    }

    @Override
    public TransactionDto getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        return modelMapper.map(transaction, TransactionDto.class);
    }

    @Override
    public List<TransactionDto> getTransactionsByAccountId(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        List<Transaction> transactions = transactionRepository.findTransactionsByAccount(account);
        List<TransactionDto> transactionDtos = new ArrayList<>();
        for (Transaction transaction : transactions) {
            transactionDtos.add(modelMapper.map(transaction, TransactionDto.class));
        }
        return transactionDtos;
    }

    @Override
    public List<TransactionDto> getTransactionsByUserId(Long userId) {
        List<Transaction> transactions = transactionRepository.findTransactionsByUserId(userId);
        List<TransactionDto> transactionDtos = new ArrayList<>();
        for (Transaction transaction : transactions) {
            transactionDtos.add(modelMapper.map(transaction, TransactionDto.class));
        }
        return transactionDtos;
    }

    @Override
    public List<TransactionDto> getTransactionsByAccountAndDateRange(Long accountId, LocalDateTime startDate, LocalDateTime endDate) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        List<Transaction> transactions = transactionRepository.findTransactionsByAccountAndDateRange(account, startDate, endDate);
        List<TransactionDto> transactionDtos = new ArrayList<>();
        for (Transaction transaction : transactions) {
            transactionDtos.add(modelMapper.map(transaction, TransactionDto.class));
        }
        return transactionDtos;
    }

    @Override
    @Transactional
    public TransactionDto deposit(Long accountId, double amount, String description) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if (!account.isActive()) {
            throw new RuntimeException("Account is not active");
        }

        // Create transaction record
        Transaction transaction = new Transaction();
        transaction.setTransactionType(AppConstants.DEPOSIT);
        transaction.setAmount(amount);
        transaction.setTransactionStatus(AppConstants.SUCCESS);
        transaction.setDescription(description != null ? description : "Deposit");
        transaction.setFromAccount(account); // For deposits, fromAccount is the same as toAccount
        transaction.setToAccount(account);

        account.deposit(amount);
        accountRepository.save(account);
        Transaction savedTransaction = transactionRepository.save(transaction);

        return modelMapper.map(savedTransaction, TransactionDto.class);
    }

    @Override
    @Transactional
    public TransactionDto withdraw(Long accountId, double amount, String description) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if (!account.isActive()) {
            throw new RuntimeException("Account is not active");
        }

        if (!account.hasSufficientBalance(amount)) {
            throw new RuntimeException("Insufficient balance");
        }

        // Check daily withdrawal limit
        Double todayWithdrawal = transactionRepository.getTodayWithdrawalAmount(account);
        if (todayWithdrawal != null && todayWithdrawal + amount > AppConstants.DAILY_WITHDRAWAL_LIMIT) {
            throw new RuntimeException("Daily withdrawal limit exceeded");
        }

        // Check monthly withdrawal limit
        Double monthlyWithdrawal = transactionRepository.getMonthlyWithdrawalAmount(account);
        if (monthlyWithdrawal != null && monthlyWithdrawal + amount > AppConstants.MONTHLY_WITHDRAWAL_LIMIT) {
            throw new RuntimeException("Monthly withdrawal limit exceeded");
        }

        // Create transaction record
        Transaction transaction = new Transaction();
        transaction.setTransactionType(AppConstants.WITHDRAWAL);
        transaction.setAmount(amount);
        transaction.setTransactionStatus(AppConstants.SUCCESS);
        transaction.setDescription(description != null ? description : "Withdrawal");
        transaction.setFromAccount(account);

        account.withdraw(amount);
        accountRepository.save(account);
        Transaction savedTransaction = transactionRepository.save(transaction);

        return modelMapper.map(savedTransaction, TransactionDto.class);
    }

    @Override
    @Transactional
    public TransactionDto transfer(Long fromAccountId, Long toAccountId, double amount, String description) {
        if (fromAccountId.equals(toAccountId)) {
            throw new RuntimeException("Cannot transfer to the same account");
        }

        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new AccountNotFoundException("From account not found"));

        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new AccountNotFoundException("To account not found"));

        if (!fromAccount.isActive() || !toAccount.isActive()) {
            throw new RuntimeException("One or both accounts are not active");
        }

        if (!fromAccount.hasSufficientBalance(amount)) {
            throw new RuntimeException("Insufficient balance in from account");
        }

        // Create transaction record
        Transaction transaction = new Transaction();
        transaction.setTransactionType(AppConstants.TRANSFER);
        transaction.setAmount(amount);
        transaction.setTransactionStatus(AppConstants.SUCCESS);
        transaction.setDescription(description != null ? description : "Transfer");
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);

        fromAccount.withdraw(amount);
        toAccount.deposit(amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        Transaction savedTransaction = transactionRepository.save(transaction);

        return modelMapper.map(savedTransaction, TransactionDto.class);
    }

    @Override
    public List<TransactionDto> getTransactionHistory(Long accountId) {
        return getTransactionsByAccountId(accountId);
    }
}