package com.example.usersm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usersm.Dto.CategoryRequestDto;
import com.example.usersm.Dto.CategoryResponseDto;
import com.example.usersm.service.CategoryService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;





@RestController
@RequestMapping("/api/v1/categorys")
public class CategoryController {
    
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping("/add-category")
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CategoryRequestDto dto){
        return ResponseEntity.ok(categoryService.createCategory(dto));
    }

    @GetMapping("/get-category")
    public ResponseEntity<List<CategoryResponseDto>> getAllCategorys(){
        return ResponseEntity.ok(categoryService.getAllCategory());
    }


    @GetMapping("/get-byid/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryByid(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getCategoryByid(id));
    }


    @DeleteMapping("/delete-byid/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category Deleted Sucesssfully!!");
    }


    @PutMapping("/update-category/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@RequestBody CategoryRequestDto dto, @PathVariable long id){
        return ResponseEntity.ok(categoryService.updateCategory(dto, id));
    }

    

}