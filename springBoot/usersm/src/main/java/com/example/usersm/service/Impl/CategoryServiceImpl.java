package com.example.usersm.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.usersm.Dto.CategoryRequestDto;
import com.example.usersm.Dto.CategoryResponseDto;
import com.example.usersm.Entity.Category;
import com.example.usersm.Exception.CategoryNotFoundException;
import com.example.usersm.Exception.NameAlreadyExistException;
import com.example.usersm.Repository.CategoryRepositry;
import com.example.usersm.controller.CategoryController;
import com.example.usersm.service.CategoryService;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepositry categoryRepositry;
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    public CategoryServiceImpl(CategoryRepositry categoryRepositry) {
        this.categoryRepositry = categoryRepositry;
    }

    private CategoryResponseDto mapToDto(Category category) {
        CategoryResponseDto dto = new CategoryResponseDto(
                category.getId(),
                category.getName());

        dto.add(
                linkTo(
                        methodOn(CategoryController.class)
                                .getCategoryByid(category.getId()))
                        .withRel("/get-byid/{id}"));
        dto.add(linkTo(methodOn(CategoryController.class)
                .getAllCategorys()).withRel("/get-category"));

        return dto;
    }

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto dto) {
        logger.info("Creating category with name: {}", dto.getName());
        if (categoryRepositry.existsByName(dto.getName())) {
            logger.warn(
                    "Category already exists: {}",
                    dto.getName());
            throw new NameAlreadyExistException("Name Already Exist");
        }

        Category category = new Category();

        category.setName(dto.getName());

        Category saveCategory = categoryRepositry.save(category);
        logger.info(
                "Category created successfully with id: {}",
                saveCategory.getId());
        return mapToDto(saveCategory);

    }

    @Override
    public List<CategoryResponseDto> getAllCategory() {
        return categoryRepositry.findAll().stream()
                .map(this::mapToDto).toList();
    }

    @Override
    public CategoryResponseDto getCategoryByid(long id) {
        Category category = categoryRepositry.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category Not Found"));
        return mapToDto(category);
    }

    @Override
    public void deleteCategory(long id) {
        Category category = categoryRepositry.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category Not Found"));
        categoryRepositry.delete(category);
    }

    @Override
    public CategoryResponseDto updateCategory(CategoryRequestDto dto, long id) {
        Category category = categoryRepositry.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category Not Found"));

        category.setName(dto.getName());

        Category updateCategory = categoryRepositry.save(category);

        return mapToDto(updateCategory);

    }

}