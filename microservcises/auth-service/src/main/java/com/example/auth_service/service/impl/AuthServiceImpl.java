package com.example.auth_service.service.impl;

import com.example.auth_service.dto.LoginRequest;
import com.example.auth_service.dto.LoginResponse;
import com.example.auth_service.dto.RefreshTokenRequest;
import com.example.auth_service.dto.RegisterRequest;
import com.example.auth_service.entity.RefreshToken;
import com.example.auth_service.entity.Role;
import com.example.auth_service.entity.User;
import com.example.auth_service.exception.EmailAlreadyExistsException;
import com.example.auth_service.exception.InvalidCredentialsException;
import com.example.auth_service.repository.UserRepository;
import com.example.auth_service.security.CustomUserDetailsService;
import com.example.auth_service.service.AuthService;
import com.example.auth_service.service.JwtService;
import com.example.auth_service.service.RefreshTokenService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final AuthenticationManager authenticationManager;
        private final CustomUserDetailsService userDetailsService;
        private final JwtService jwtService;
        private final RefreshTokenService refreshTokenService;

        public AuthServiceImpl(
                        UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        AuthenticationManager authenticationManager,
                        CustomUserDetailsService userDetailsService,
                        JwtService jwtService,
                        RefreshTokenService refreshTokenService) {

                this.userRepository = userRepository;
                this.passwordEncoder = passwordEncoder;
                this.authenticationManager = authenticationManager;
                this.userDetailsService = userDetailsService;
                this.jwtService = jwtService;
                this.refreshTokenService = refreshTokenService;
        }

        @Override
        public User register(RegisterRequest request) {

                if (userRepository.existsByEmail(request.getEmail())) {
                        throw new EmailAlreadyExistsException("Email already registered");
                }

                User user = new User();

                user.setName(request.getName());
                user.setEmail(request.getEmail());

                user.setPassword(
                                passwordEncoder.encode(request.getPassword()));

                user.setRole(Role.ROLE_USER);

                return userRepository.save(user);
        }

        @Override
        public LoginResponse login(LoginRequest request) {

                try {

                        authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(
                                                        request.getEmail(),
                                                        request.getPassword()));

                } catch (Exception ex) {

                        throw new InvalidCredentialsException(
                                        "Invalid email or password");
                }

                User user = userRepository
                                .findByEmail(request.getEmail())
                                .orElseThrow(() -> new RuntimeException("User not found"));

                UserDetails userDetails = userDetailsService.loadUserByUsername(
                                request.getEmail());

                String accessToken = jwtService.generateAccessToken(userDetails);

                RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

                LoginResponse response = new LoginResponse(
                                accessToken,
                                refreshToken.getToken());

                return response;
        }

        @Override
        public LoginResponse refreshToken(
                        RefreshTokenRequest request) {

                // Find refresh token
                RefreshToken refreshToken = refreshTokenService.findByToken(
                                request.getRefreshToken());

                // Check expiration
                refreshTokenService.verifyExpiration(
                                refreshToken);

                // Get user
                User user = refreshToken.getUser();

                // Load user details
                UserDetails userDetails = userDetailsService.loadUserByUsername(
                                user.getEmail());

                // Generate new access token
                String newAccessToken = jwtService.generateAccessToken(
                                userDetails);

                return new LoginResponse(
                                newAccessToken,
                                refreshToken.getToken());
        }

        @Transactional
        public void logout(String refreshToken) {

                refreshTokenService.deleteByToken(refreshToken);
        }
}