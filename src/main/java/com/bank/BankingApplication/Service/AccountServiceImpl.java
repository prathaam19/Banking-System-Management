package com.bank.BankingApplication.Service;

import com.bank.BankingApplication.DTO.AccountDto;
import com.bank.BankingApplication.Entity.Account;
import com.bank.BankingApplication.Entity.Branch;
import com.bank.BankingApplication.Entity.User;
import com.bank.BankingApplication.Exception.AccountNotFoundException;
import com.bank.BankingApplication.Exception.UserNotFoundException;
import com.bank.BankingApplication.Repository.AccountRepository;
import com.bank.BankingApplication.Repository.BranchRepository;
import com.bank.BankingApplication.Repository.UserRepository;
import com.bank.BankingApplication.constants.AppConstants;
import com.bank.BankingApplication.mapper.AccountMapper;
import com.bank.BankingApplication.util.AccountNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final AccountMapper accountMapper;
    private final AccountNumberGenerator accountNumberGenerator;

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        // Validate user exists
        User user = userRepository.findById(accountDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + accountDto.getUserId()));

        // Generate unique account number
        String accountNumber;
        do {
            accountNumber = accountNumberGenerator.generateAccountNumber();
        } while (accountRepository.existsByAccountNumber(accountNumber));

        // Create account entity
        Account account = accountMapper.toEntity(accountDto);
        account.setAccountNumber(accountNumber);
        account.setAccountType(accountDto.getAccountType() != null ? accountDto.getAccountType() : AppConstants.SAVINGS_ACCOUNT);
        account.setAccountStatus(AppConstants.ACCOUNT_ACTIVE);
        account.setUser(user);

        // Set branch if provided
        if (accountDto.getBranchId() != null) {
            Branch branch = branchRepository.findById(accountDto.getBranchId())
                    .orElseThrow(() -> new RuntimeException("Branch not found with ID: " + accountDto.getBranchId()));
            account.setBranch(branch);
        }

        Account savedAccount = accountRepository.save(account);
        return accountMapper.toDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account does not exist with ID: " + id));
        return accountMapper.toDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account does not exist with ID: " + id));

        if (!account.isActive()) {
            throw new RuntimeException("Account is not active");
        }

        account.deposit(amount);
        Account savedAccount = accountRepository.save(account);
        return accountMapper.toDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account does not exist with ID: " + id));

        if (!account.isActive()) {
            throw new RuntimeException("Account is not active");
        }

        if (!account.hasSufficientBalance(amount)) {
            throw new RuntimeException("Insufficient balance");
        }

        account.withdraw(amount);
        Account savedAccount = accountRepository.save(account);
        return accountMapper.toDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountDto> accountDtos = new ArrayList<>();
        for (Account account : accounts) {
            accountDtos.add(accountMapper.toDto(account));
        }
        return accountDtos;
    }

    @Override
    public List<AccountDto> getAccountsByUserId(Long userId) {
        List<Account> accounts = accountRepository.findByUserId(userId);
        List<AccountDto> accountDtos = new ArrayList<>();
        for (Account account : accounts) {
            accountDtos.add(accountMapper.toDto(account));
        }
        return accountDtos;
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account does not exist with ID: " + id));
        accountRepository.deleteById(id);
    }

    @Override
    public AccountDto updateAccount(Long id, AccountDto accountDto) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account does not exist with ID: " + id));

        accountMapper.updateEntityFromDto(accountDto, account);
        Account savedAccount = accountRepository.save(account);
        return accountMapper.toDto(savedAccount);
    }

    @Override
    public AccountDto getAccountByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with number: " + accountNumber));
        return accountMapper.toDto(account);
    }
}