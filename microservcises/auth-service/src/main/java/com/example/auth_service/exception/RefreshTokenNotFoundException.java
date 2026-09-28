package com.example.auth_service.exception;

public class RefreshTokenNotFoundException
        extends RuntimeException {

    public RefreshTokenNotFoundException(String message) {
        super(message);
    }
}