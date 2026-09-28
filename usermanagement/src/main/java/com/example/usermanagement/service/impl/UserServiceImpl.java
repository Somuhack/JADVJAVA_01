package com.example.usermanagement.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.usermanagement.dto.UserRequestDto;
import com.example.usermanagement.dto.UserResponseDto;
import com.example.usermanagement.entity.User;
import com.example.usermanagement.exception.UserAlreadyExistsException;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	  private final UserRepository userRepository ;
	  User user = new User();
   
	  public UserServiceImpl(UserRepository userRepository) {
		       this.userRepository=userRepository;  
	  }
  
	  @Override
	 public UserResponseDto createUser(UserRequestDto dto) {
		  if(userRepository.existsByEmail(dto.getEmail())) {
			  throw new UserAlreadyExistsException("Email already exists");
		  }
		  
		  user.setName(dto.getName());
		  user.setEmail(dto.getEmail());
		  
		  User saveUser =userRepository.save(null);
		  
		  return new UserResponseDto(saveUser.getId(),saveUser.getName(),saveUser.getEmail());
	  }
	  @Override
	  public List<UserResponseDto> getAllUsers(){
		  return userRepository.findAll()
	                .stream()
	                .map(user -> new UserResponseDto(
	                        user.getId(),
	                        user.getName(),
	                        user.getEmail()
	                ))
	                .collect(Collectors.toList());
		  	  }
}
