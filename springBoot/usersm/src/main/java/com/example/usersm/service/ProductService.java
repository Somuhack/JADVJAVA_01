package com.example.usersm.service;

import java.io.IOException;

import org.springframework.data.domain.Page;

import com.example.usersm.Dto.ProductRequestDto;
import com.example.usersm.Dto.ProductResponseDto;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto dto) throws IOException;
    Page<ProductResponseDto> getAllProduct(int page,
            int size, String sortBy, String direction, Long categoryId,
            Double minPrice,
            Double maxPrice,
            String name,
            Double minRating,
            Double maxRating);

    ProductResponseDto getProductByid(long id);

    void deleteProduct(long id);

    ProductResponseDto updateProduct(ProductRequestDto dto, long id) throws IOException;

}