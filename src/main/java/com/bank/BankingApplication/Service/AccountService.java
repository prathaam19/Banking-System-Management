package com.bank.BankingApplication.Service;

import com.bank.BankingApplication.DTO.AccountDto;
import java.util.List;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountById(Long id);

    AccountDto getAccountByAccountNumber(String accountNumber);

    AccountDto deposit(Long id, double amount);

    AccountDto withdraw(Long id, double amount);

    AccountDto updateAccount(Long id, AccountDto accountDto);

    List<AccountDto> getAllAccounts();

    List<AccountDto> getAccountsByUserId(Long userId);

    void deleteAccount(Long id);
}

