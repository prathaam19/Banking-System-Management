package com.bank.BankingApplication.Controller;

import com.bank.BankingApplication.DTO.AccountDto;
import com.bank.BankingApplication.Service.AccountService;
import com.bank.BankingApplication.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Account Controller", description = "Endpoints for Account Management")
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "Create a new bank account")
    @PostMapping
    public ResponseEntity<ApiResponse<AccountDto>> addAccount(@Valid @RequestBody AccountDto accountDto) {
        AccountDto createdAccount = accountService.createAccount(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Account created successfully", createdAccount));
    }

    @Operation(summary = "Get account details by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountDto>> getAccountById(@PathVariable Long id) {
        AccountDto accountDto = accountService.getAccountById(id);
        return ResponseEntity.ok(ApiResponse.success(accountDto));
    }

    @Operation(summary = "Get account details by account number")
    @GetMapping("/number/{accountNumber}")
    public ResponseEntity<ApiResponse<AccountDto>> getAccountByAccountNumber(@PathVariable String accountNumber) {
        AccountDto accountDto = accountService.getAccountByAccountNumber(accountNumber);
        return ResponseEntity.ok(ApiResponse.success(accountDto));
    }

    @Operation(summary = "Deposit money into an account")
    @PutMapping("/{id}/deposit")
    public ResponseEntity<ApiResponse<AccountDto>> deposit(@PathVariable Long id,
                                                           @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        AccountDto accountDto = accountService.deposit(id, amount);
        return ResponseEntity.ok(ApiResponse.success("Deposit successful", accountDto));
    }

    @Operation(summary = "Withdraw money from an account")
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<ApiResponse<AccountDto>> withdraw(@PathVariable Long id,
                                                            @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        AccountDto accountDto = accountService.withdraw(id, amount);
        return ResponseEntity.ok(ApiResponse.success("Withdrawal successful", accountDto));
    }

    @Operation(summary = "Get a list of all bank accounts")
    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountDto>>> getAllAccounts() {
        List<AccountDto> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(ApiResponse.success(accounts));
    }

    @Operation(summary = "Get accounts by user ID")
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<AccountDto>>> getAccountsByUserId(@PathVariable Long userId) {
        List<AccountDto> accounts = accountService.getAccountsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(accounts));
    }

    @Operation(summary = "Update account details")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountDto>> updateAccount(@PathVariable Long id,
                                                                 @Valid @RequestBody AccountDto accountDto) {
        AccountDto updatedAccount = accountService.updateAccount(id, accountDto);
        return ResponseEntity.ok(ApiResponse.success("Account updated successfully", updatedAccount));
    }

    @Operation(summary = "Delete an existing account")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok(ApiResponse.success("Account deleted successfully"));
    }
}