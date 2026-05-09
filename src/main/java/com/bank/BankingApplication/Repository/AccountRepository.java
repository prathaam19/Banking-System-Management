package com.bank.BankingApplication.Repository;

import com.bank.BankingApplication.Entity.Account;
import com.bank.BankingApplication.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByUser(User user);

    List<Account> findByUserId(Long userId);

    boolean existsByAccountNumber(String accountNumber);

    @Query("SELECT a FROM Account a WHERE a.user.id = :userId AND a.accountStatus = 'ACTIVE'")
    List<Account> findActiveAccountsByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(a) FROM Account a WHERE a.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);
}
