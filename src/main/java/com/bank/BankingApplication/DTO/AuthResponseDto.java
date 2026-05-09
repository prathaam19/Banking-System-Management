package com.bank.BankingApplication.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {

    private String token;
    private String tokenType = "Bearer";
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private LocalDateTime expiresAt;

    public AuthResponseDto(String token, String username, String email, String firstName, String lastName, String role) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.expiresAt = LocalDateTime.now().plusHours(24); // Assuming 24 hour expiry
    }
}