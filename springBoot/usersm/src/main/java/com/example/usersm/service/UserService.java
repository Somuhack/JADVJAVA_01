package com.example.usersm.service;

import java.util.List;
import java.util.Map;

import com.example.usersm.Dto.UserRequestDto;
import com.example.usersm.Dto.UserResponceDto;

public interface UserService {
    UserResponceDto createUser(UserRequestDto dto);    
    List<UserResponceDto> getAllUsers();
    UserResponceDto getUserByid(Long id);
    void deleteUser(Long id);
    UserResponceDto UpadteUser(UserRequestDto dto,Long id);
    public UserResponceDto updateUserByPatch(Long id, Map<String, Object> fields);
}
