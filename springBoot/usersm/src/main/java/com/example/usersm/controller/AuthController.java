package com.example.usersm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.usersm.Dto.JwtResponseDto;
import com.example.usersm.Dto.LoginRequestDto;
import com.example.usersm.Dto.RefreshTokenRequestDto;
import com.example.usersm.Dto.RegisterRequestDto;
import com.example.usersm.service.AuthService;

@RestController
@RequestMapping("/api/auth/v1")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequestDto dto) {

        authService.register(dto);

        return ResponseEntity
                .ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(
            @RequestBody LoginRequestDto dto) {

        return ResponseEntity.ok(
                authService.login(dto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponseDto> refresh(
            @RequestBody RefreshTokenRequestDto request) {

        JwtResponseDto response = authService.refreshToken(request.getRefreshToken());

        return ResponseEntity.ok(response);
    }
}