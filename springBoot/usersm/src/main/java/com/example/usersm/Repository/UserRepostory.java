package com.example.usersm.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.usersm.Entity.User;
public interface UserRepostory extends JpaRepository<User,Long> {
     Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
