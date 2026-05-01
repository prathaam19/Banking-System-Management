package com.bank.BankingApplication.Repository;

import com.bank.BankingApplication.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {

}
