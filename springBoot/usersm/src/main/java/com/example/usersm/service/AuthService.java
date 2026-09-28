package com.example.usersm.service;

import com.example.usersm.Dto.JwtResponseDto;
import com.example.usersm.Dto.LoginRequestDto;
import com.example.usersm.Dto.RegisterRequestDto;

public interface AuthService {
      void register(RegisterRequestDto dto);
      JwtResponseDto login(LoginRequestDto dto);
      JwtResponseDto refreshToken(String refreshToken);

}
