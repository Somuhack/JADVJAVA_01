package com.example.usersm.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.usersm.Entity.Product;



public interface ProductRepositry extends JpaRepository<Product,Long>,JpaSpecificationExecutor<Product> {
    
    List<Product> findByCategoryId(Long categoryId);
    @Override
    @EntityGraph(attributePaths = {"category"})
    Page<Product> findAll(Pageable pageable);
}