package com.bank.BankingApplication.Controller;

import com.bank.BankingApplication.DTO.TransactionDto;
import com.bank.BankingApplication.Service.TransactionService;
import com.bank.BankingApplication.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction Controller", description = "Endpoints for banking transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Get transaction by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TransactionDto>> getTransactionById(@PathVariable Long id) {
        TransactionDto transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(ApiResponse.success(transaction));
    }

    @Operation(summary = "Get transactions by account ID")
    @GetMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse<List<TransactionDto>>> getTransactionsByAccountId(@PathVariable Long accountId) {
        List<TransactionDto> transactions = transactionService.getTransactionsByAccountId(accountId);
        return ResponseEntity.ok(ApiResponse.success(transactions));
    }

    @Operation(summary = "Get transactions by user ID")
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<TransactionDto>>> getTransactionsByUserId(@PathVariable Long userId) {
        List<TransactionDto> transactions = transactionService.getTransactionsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(transactions));
    }

    @Operation(summary = "Get transactions by account and date range")
    @GetMapping("/account/{accountId}/date-range")
    public ResponseEntity<ApiResponse<List<TransactionDto>>> getTransactionsByAccountAndDateRange(
            @PathVariable Long accountId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<TransactionDto> transactions = transactionService.getTransactionsByAccountAndDateRange(accountId, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(transactions));
    }

    @Operation(summary = "Deposit money into account")
    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<ApiResponse<TransactionDto>> deposit(
            @PathVariable Long accountId,
            @RequestBody Map<String, Object> request) {
        Double amount = Double.valueOf(request.get("amount").toString());
        String description = request.get("description") != null ? request.get("description").toString() : null;

        TransactionDto transaction = transactionService.deposit(accountId, amount, description);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Deposit successful", transaction));
    }

    @Operation(summary = "Withdraw money from account")
    @PostMapping("/withdraw/{accountId}")
    public ResponseEntity<ApiResponse<TransactionDto>> withdraw(
            @PathVariable Long accountId,
            @RequestBody Map<String, Object> request) {
        Double amount = Double.valueOf(request.get("amount").toString());
        String description = request.get("description") != null ? request.get("description").toString() : null;

        TransactionDto transaction = transactionService.withdraw(accountId, amount, description);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Withdrawal successful", transaction));
    }

    @Operation(summary = "Transfer money between accounts")
    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<TransactionDto>> transfer(@RequestBody Map<String, Object> request) {
        Long fromAccountId = Long.valueOf(request.get("fromAccountId").toString());
        Long toAccountId = Long.valueOf(request.get("toAccountId").toString());
        Double amount = Double.valueOf(request.get("amount").toString());
        String description = request.get("description") != null ? request.get("description").toString() : null;

        TransactionDto transaction = transactionService.transfer(fromAccountId, toAccountId, amount, description);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Transfer successful", transaction));
    }

    @Operation(summary = "Get transaction history for account")
    @GetMapping("/history/{accountId}")
    public ResponseEntity<ApiResponse<List<TransactionDto>>> getTransactionHistory(@PathVariable Long accountId) {
        List<TransactionDto> transactions = transactionService.getTransactionHistory(accountId);
        return ResponseEntity.ok(ApiResponse.success(transactions));
    }
}