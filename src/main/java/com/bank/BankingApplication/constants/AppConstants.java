package com.bank.BankingApplication.constants;

public class AppConstants {

    // JWT Constants
    public static final String JWT_SECRET = "YourUltraSecureSecretKeyForBankingAppThatIsVeryLongAndSecure2026";
    public static final long JWT_EXPIRATION = 86400000; // 24 hours in milliseconds

    // Account Types
    public static final String SAVINGS_ACCOUNT = "SAVINGS";
    public static final String CURRENT_ACCOUNT = "CURRENT";
    public static final String FIXED_DEPOSIT = "FIXED_DEPOSIT";

    // Transaction Types
    public static final String DEPOSIT = "DEPOSIT";
    public static final String WITHDRAWAL = "WITHDRAWAL";
    public static final String TRANSFER = "TRANSFER";

    // Transaction Status
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";
    public static final String PENDING = "PENDING";

    // User Roles
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    // Account Status
    public static final String ACCOUNT_ACTIVE = "ACTIVE";
    public static final String ACCOUNT_INACTIVE = "INACTIVE";
    public static final String ACCOUNT_SUSPENDED = "SUSPENDED";

    // Minimum Balance Requirements
    public static final double MINIMUM_BALANCE = 500.0;
    public static final double OVERDRAFT_LIMIT = 1000.0;

    // Transaction Limits
    public static final double DAILY_WITHDRAWAL_LIMIT = 50000.0;
    public static final double MONTHLY_WITHDRAWAL_LIMIT = 200000.0;

    private AppConstants() {
        // Private constructor to prevent instantiation
    }
}