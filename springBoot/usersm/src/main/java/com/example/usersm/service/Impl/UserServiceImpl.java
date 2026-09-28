package com.example.usersm.service.Impl;

import org.springframework.stereotype.Service;


import com.example.usersm.Dto.UserRequestDto;
import com.example.usersm.Dto.UserResponceDto;
import com.example.usersm.Entity.User;
import com.example.usersm.Exception.UserAlreadyExistException;
import com.example.usersm.Exception.UserNotFoundException;
import com.example.usersm.Repository.UserRepostory;
import com.example.usersm.service.UserService;

import java.util.List;
import java.util.Map;;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepostory userRepostory;

    public UserServiceImpl(UserRepostory userRepostory) {
        this.userRepostory = userRepostory;
    }

    @Override
    public UserResponceDto createUser(UserRequestDto dto) {
        if (userRepostory.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistException("Email All ready Exist ");
        }
        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        User saveUser = userRepostory.save(user);
        return new UserResponceDto(saveUser.getId(), saveUser.getName(), saveUser.getEmail());
    }
    @Override
    public List<UserResponceDto> getAllUsers() {
        return userRepostory.findAll().stream()
                .map(user -> new UserResponceDto(user.getId(), user.getName(), user.getEmail())).toList();
    }
    @Override
    public UserResponceDto getUserByid(Long id) {
        User user = userRepostory.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));
        return new UserResponceDto(user.getId(), user.getName(), user.getEmail());
    }
    @Override
    public void deleteUser(Long id) {
        User user = userRepostory.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));
        userRepostory.delete(user);
    }
    @Override
    public UserResponceDto UpadteUser(UserRequestDto dto, Long id) {
        User user = userRepostory.findById(id).orElseThrow(() -> new UserNotFoundException("User not Found"));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        User Updateuser = userRepostory.save(user);
        return new UserResponceDto(Updateuser.getId(), Updateuser.getName(), Updateuser.getEmail());
    }

    @Override
    public UserResponceDto updateUserByPatch(Long id, Map<String, Object> fields) {
        
        User existingUser = userRepostory.findById(id).orElseThrow(() -> new UserNotFoundException("User not Found"));
        fields.forEach((key, value) -> {
            switch (key) {
                case "name":
                    existingUser.setName((String) value);
                    break;
                case "email":
                    existingUser.setEmail((String) value);
                    break;
                default:
                    // Ignore unknown fields or fields like 'id'
                    break;
            }

        });
        User updateUserbyPatch = userRepostory.save(existingUser);
        return new UserResponceDto(updateUserbyPatch.getId(), updateUserbyPatch.getName(),
                updateUserbyPatch.getEmail());
    }
}
