package com.example.usersm.Repository;

import com.example.usersm.Entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepositry extends JpaRepository<Category,Long> {    
    boolean existsByName(String name);
}