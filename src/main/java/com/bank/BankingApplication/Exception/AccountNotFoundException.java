package com.bank.BankingApplication.Exception;

public class AccountNotFoundException extends RuntimeException
{
    public AccountNotFoundException(String message) {
        super(message);
    }
}
