package com.bank.BankingApplication.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private Long id;

    @NotBlank(message = "Account holder name is required")
    private String accountHolderName;

    private String accountNumber;

    private String accountType;

    @PositiveOrZero(message = "Balance cannot be negative")
    private double balance;

    private String accountStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // User information
    @NotNull(message = "User ID is required")
    private Long userId;

    private String username;

    // Branch information
    private Long branchId;

    private String branchName;

    private String branchCode;
}
