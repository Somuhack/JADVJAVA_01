package com.example.auth_service.service;

import com.example.auth_service.entity.RefreshToken;
import com.example.auth_service.entity.User;
import com.example.auth_service.exception.RefreshTokenExpiredException;
import com.example.auth_service.exception.RefreshTokenNotFoundException;
import com.example.auth_service.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

        private final RefreshTokenRepository refreshTokenRepository;

        @Value("${jwt.refresh-expiration}")
        private long refreshExpiration;

        public RefreshTokenService(
                        RefreshTokenRepository refreshTokenRepository) {

                this.refreshTokenRepository = refreshTokenRepository;
        }

        public RefreshToken createRefreshToken(User user) {

                RefreshToken refreshToken = new RefreshToken();

                refreshToken.setUser(user);
                refreshToken.setToken(UUID.randomUUID().toString());
                refreshToken.setExpiryDate(
                                Instant.now().plusMillis(refreshExpiration));

                return refreshTokenRepository.save(refreshToken);
        }

        public RefreshToken findByToken(String token) {

                return refreshTokenRepository
                                .findByToken(token)
                                .orElseThrow(() -> new RefreshTokenNotFoundException(
                                                "Refresh token not found"));
        }

        public RefreshToken verifyExpiration(
                        RefreshToken refreshToken) {

                if (refreshToken.getExpiryDate()
                                .isBefore(Instant.now())) {

                        refreshTokenRepository.delete(refreshToken);

                        throw new RefreshTokenExpiredException(
                                        "Refresh token Exipired");
                }

                return refreshToken;
        }


        @Transactional 
        public void deleteByToken(String token) {
                refreshTokenRepository.deleteByToken(token);
        }

}