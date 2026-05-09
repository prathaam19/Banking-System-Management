package com.bank.BankingApplication.security;

import com.bank.BankingApplication.constants.AppConstants;

public enum Role {
    USER(AppConstants.ROLE_USER),
    ADMIN(AppConstants.ROLE_ADMIN);

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Role fromString(String value) {
        for (Role role : Role.values()) {
            if (role.value.equals(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + value);
    }
}