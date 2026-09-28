package com.example.usersm.service;

import java.util.List;

import com.example.usersm.Dto.CategoryRequestDto;
import com.example.usersm.Dto.CategoryResponseDto;

public interface CategoryService {
    
    CategoryResponseDto createCategory(CategoryRequestDto dto);

    List<CategoryResponseDto> getAllCategory();

    CategoryResponseDto getCategoryByid(long id);

    void deleteCategory(long id);

    CategoryResponseDto updateCategory(CategoryRequestDto dto, long id);

}