package com.example.auth_service.dto;

public class LoginResponse {

    private String accessToken;
    private String tokenType;
    private String refreshToken;
    public LoginResponse(String accessToken,  String refreshToken) {
        this.accessToken = accessToken;
         this.refreshToken = refreshToken;
        this.tokenType = "Bearer";
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }
      public String getRefreshToken() {
        return refreshToken;
    }
}