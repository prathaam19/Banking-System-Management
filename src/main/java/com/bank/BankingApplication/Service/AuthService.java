package com.bank.BankingApplication.Service;

import com.bank.BankingApplication.DTO.AuthResponseDto;
import com.bank.BankingApplication.DTO.LoginRequestDto;
import com.bank.BankingApplication.DTO.RegisterRequestDto;

public interface AuthService {

    AuthResponseDto login(LoginRequestDto loginRequest);

    AuthResponseDto register(RegisterRequestDto registerRequest);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}