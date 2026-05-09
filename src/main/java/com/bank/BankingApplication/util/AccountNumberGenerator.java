package com.bank.BankingApplication.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class AccountNumberGenerator {

    private static final SecureRandom random = new SecureRandom();
    private static final String BANK_CODE = "BANK";
    private static final int ACCOUNT_NUMBER_LENGTH = 10;

    /**
     * Generates a unique account number
     * Format: BANK + YYYYMMDD + Random 6-digit number
     * Example: BANK20231201001234
     */
    public String generateAccountNumber() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomPart = generateRandomDigits(6);
        return BANK_CODE + timestamp + randomPart;
    }

    /**
     * Generates a random numeric string of specified length
     */
    private String generateRandomDigits(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * Validates if the account number follows the correct format
     */
    public boolean isValidAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() != ACCOUNT_NUMBER_LENGTH + BANK_CODE.length()) {
            return false;
        }
        return accountNumber.startsWith(BANK_CODE) && accountNumber.substring(BANK_CODE.length()).matches("\\d+");
    }
}