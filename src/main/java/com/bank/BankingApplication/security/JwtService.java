package com.bank.BankingApplication.security;

import com.bank.BankingApplication.Entity.User;
import com.bank.BankingApplication.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtUtil jwtUtil;

    /**
     * Generate JWT token for user
     */
    public String generateToken(User user) {
        return jwtUtil.generateToken(user.getUsername());
    }

    /**
     * Extract username from JWT token
     */
    public String extractUsername(String token) {
        return jwtUtil.extractUsername(token);
    }

    /**
     * Validate JWT token
     */
    public boolean validateToken(String token, String username) {
        return jwtUtil.validateToken(token, username);
    }

    /**
     * Validate JWT token without username
     */
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }
}