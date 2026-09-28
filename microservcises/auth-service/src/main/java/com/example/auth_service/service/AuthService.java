package com.example.auth_service.service;

import com.example.auth_service.dto.LoginRequest;
import com.example.auth_service.dto.LoginResponse;
import com.example.auth_service.dto.RefreshTokenRequest;
import com.example.auth_service.dto.RegisterRequest;
import com.example.auth_service.entity.User;

public interface AuthService {

    User register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    LoginResponse refreshToken(
            RefreshTokenRequest request);

    void logout(String refreshToken);
}