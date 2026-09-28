package com.example.usermanagement.service;


import java.util.List;

import com.example.usermanagement.dto.UserRequestDto;
import com.example.usermanagement.dto.UserResponseDto;
public interface UserService {
    UserResponseDto createUser(UserRequestDto dto);
    List<UserResponseDto> getAllUsers();
}
