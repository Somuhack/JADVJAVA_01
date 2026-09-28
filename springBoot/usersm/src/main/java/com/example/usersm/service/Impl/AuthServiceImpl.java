package com.example.usersm.service.Impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.usersm.Dto.JwtResponseDto;
import com.example.usersm.Dto.LoginRequestDto;
import com.example.usersm.Dto.RegisterRequestDto;
import com.example.usersm.Entity.Role;
import com.example.usersm.Entity.User;
import com.example.usersm.Exception.UserAlreadyExistException;
import com.example.usersm.Exception.UserNotFoundException;
import com.example.usersm.Repository.UserRepostory;
import com.example.usersm.service.AuthService;
import com.example.usersm.service.CustomUserDetailsService;
import com.example.usersm.service.JwtService;
import com.example.usersm.service.RefreshTokenService;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepostory userRepostory;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public AuthServiceImpl(
            UserRepostory userRepostory,
            PasswordEncoder passwordEncoder,
            CustomUserDetailsService customUserDetailsService,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.userRepostory = userRepostory;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(RegisterRequestDto dto) {

        if (userRepostory.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistException("User Already Exist");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.ROLE_USER);
        userRepostory.save(user);
    }

    @Override
    public JwtResponseDto login(LoginRequestDto dto) {

        // if(!userRepostory.existsByEmail(dto.getEmail())){
        // throw new UserNotFoundException("User Not Found");
        // }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        User user = userRepostory.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(dto.getEmail());

        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(user);

        // String token = jwtService.generateToken(userDetails);

        return new JwtResponseDto(accessToken, refreshToken);
    }

    @Override
    public JwtResponseDto refreshToken(String refreshToken) {

        String email;

        try {
            email = jwtService.extractUsername(refreshToken);
        } catch (Exception e) {
            throw new RuntimeException("Invalid or expired refresh token");
        }

        userRepostory.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        String newAccessToken = jwtService.generateAccessToken(userDetails);

        return new JwtResponseDto(
                newAccessToken,
                refreshToken);
    }
}
