package com.example.usersm.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.usersm.Entity.User;
import com.example.usersm.Exception.UserNotFoundException;
import com.example.usersm.Repository.UserRepostory;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepostory userRepostory;

     public CustomUserDetailsService(UserRepostory userRepostory) {
        this.userRepostory = userRepostory;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException{

        User user = userRepostory.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User Not Found"));

        return org.springframework.security.core.userdetails.User
        .withUsername(user.getEmail())
        .password(user.getPassword()) 
        .authorities(user.getRole().name())
        .build();

    }
    
}
