package com.bank.BankingApplication.Repository;

import com.bank.BankingApplication.Entity.Account;
import com.bank.BankingApplication.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByFromAccount(Account fromAccount);

    List<Transaction> findByToAccount(Account toAccount);

    List<Transaction> findByFromAccountOrToAccount(Account fromAccount, Account toAccount);

    @Query("SELECT t FROM Transaction t WHERE (t.fromAccount = :account OR t.toAccount = :account) ORDER BY t.transactionDate DESC")
    List<Transaction> findTransactionsByAccount(@Param("account") Account account);

    @Query("SELECT t FROM Transaction t WHERE t.fromAccount.user.id = :userId OR t.toAccount.user.id = :userId ORDER BY t.transactionDate DESC")
    List<Transaction> findTransactionsByUserId(@Param("userId") Long userId);

    @Query("SELECT t FROM Transaction t WHERE (t.fromAccount = :account OR t.toAccount = :account) AND t.transactionDate BETWEEN :startDate AND :endDate ORDER BY t.transactionDate DESC")
    List<Transaction> findTransactionsByAccountAndDateRange(
            @Param("account") Account account,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.fromAccount = :account AND t.transactionType = 'WITHDRAWAL' AND t.transactionStatus = 'SUCCESS' AND DATE(t.transactionDate) = CURRENT_DATE")
    Double getTodayWithdrawalAmount(@Param("account") Account account);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.fromAccount = :account AND t.transactionType = 'WITHDRAWAL' AND t.transactionStatus = 'SUCCESS' AND MONTH(t.transactionDate) = MONTH(CURRENT_DATE) AND YEAR(t.transactionDate) = YEAR(CURRENT_DATE)")
    Double getMonthlyWithdrawalAmount(@Param("account") Account account);
}