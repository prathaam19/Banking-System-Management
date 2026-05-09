package com.bank.BankingApplication.Controller;

import com.bank.BankingApplication.DTO.AuthResponseDto;
import com.bank.BankingApplication.DTO.LoginRequestDto;
import com.bank.BankingApplication.DTO.RegisterRequestDto;
import com.bank.BankingApplication.Service.AuthService;
import com.bank.BankingApplication.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "Endpoints for user authentication and registration")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "User login")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDto>> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        AuthResponseDto authResponse = authService.login(loginRequest);
        return ResponseEntity.ok(ApiResponse.success("Login successful", authResponse));
    }

    @Operation(summary = "User registration")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponseDto>> register(@Valid @RequestBody RegisterRequestDto registerRequest) {
        AuthResponseDto authResponse = authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully", authResponse));
    }

    @Operation(summary = "Check username availability")
    @GetMapping("/check-username/{username}")
    public ResponseEntity<ApiResponse<Boolean>> checkUsername(@PathVariable String username) {
        boolean exists = authService.existsByUsername(username);
        return ResponseEntity.ok(ApiResponse.success(!exists));
    }

    @Operation(summary = "Check email availability")
    @GetMapping("/check-email/{email}")
    public ResponseEntity<ApiResponse<Boolean>> checkEmail(@PathVariable String email) {
        boolean exists = authService.existsByEmail(email);
        return ResponseEntity.ok(ApiResponse.success(!exists));
    }
}