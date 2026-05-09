package com.bank.BankingApplication.mapper;

import com.bank.BankingApplication.DTO.AccountDto;
import com.bank.BankingApplication.Entity.Account;
import com.bank.BankingApplication.Entity.Branch;
import com.bank.BankingApplication.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public AccountDto toDto(Account account) {
        if (account == null) {
            return null;
        }

        AccountDto dto = new AccountDto();
        dto.setId(account.getId());
        dto.setAccountHolderName(account.getAccountHolderName());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setAccountType(account.getAccountType());
        dto.setBalance(account.getBalance());
        dto.setAccountStatus(account.getAccountStatus());
        dto.setCreatedAt(account.getCreatedAt());
        dto.setUpdatedAt(account.getUpdatedAt());

        // Map user information
        if (account.getUser() != null) {
            dto.setUserId(account.getUser().getId());
            dto.setUsername(account.getUser().getUsername());
        }

        // Map branch information
        if (account.getBranch() != null) {
            dto.setBranchId(account.getBranch().getId());
            dto.setBranchName(account.getBranch().getBranchName());
            dto.setBranchCode(account.getBranch().getBranchCode());
        }

        return dto;
    }

    public Account toEntity(AccountDto dto) {
        if (dto == null) {
            return null;
        }

        Account account = new Account();
        account.setId(dto.getId());
        account.setAccountHolderName(dto.getAccountHolderName());
        account.setAccountNumber(dto.getAccountNumber());
        account.setAccountType(dto.getAccountType());
        account.setBalance(dto.getBalance());
        account.setAccountStatus(dto.getAccountStatus());

        // Note: User and Branch relationships should be set separately
        // as they require repository access

        return account;
    }

    public void updateEntityFromDto(AccountDto dto, Account account) {
        if (dto == null || account == null) {
            return;
        }

        if (dto.getAccountHolderName() != null) {
            account.setAccountHolderName(dto.getAccountHolderName());
        }
        if (dto.getAccountType() != null) {
            account.setAccountType(dto.getAccountType());
        }
        if (dto.getAccountStatus() != null) {
            account.setAccountStatus(dto.getAccountStatus());
        }
    }
}