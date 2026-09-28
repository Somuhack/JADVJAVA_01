package com.example.usersm.Dto;

public class JwtResponseDto {

    private String accessToken;
    private String refreshToken;

    public JwtResponseDto(
            String accessToken,
            String refreshToken) {

        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}