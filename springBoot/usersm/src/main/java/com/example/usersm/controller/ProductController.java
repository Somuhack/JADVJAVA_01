package com.example.usersm.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.usersm.Dto.ProductRequestDto;
import com.example.usersm.Dto.ProductResponseDto;
import com.example.usersm.service.ProductService;

import jakarta.validation.Valid;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    public final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/add-product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @ModelAttribute ProductRequestDto dto)
            throws IOException {
        return ResponseEntity.ok(productService.createProduct(dto));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/get-product")
    public ResponseEntity<Page<ProductResponseDto>> getAllProduct(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction, @RequestParam(required = false) Long categoryId,

            @RequestParam(required = false) Double minPrice,

            @RequestParam(required = false) Double maxPrice,

            @RequestParam(required = false) String name, @RequestParam(required = false) Double minRating,

            @RequestParam(required = false) Double maxRating) {
        return ResponseEntity.ok(productService.getAllProduct(page, size, sortBy, direction, categoryId, minPrice,
                maxPrice, name, minRating, maxRating));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/get-byid/{id}")
    public ResponseEntity<ProductResponseDto> getProductByid(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductByid(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/delete-byid/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product Deleted Sucesssfully!!");
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/update-product/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody ProductRequestDto dto, @PathVariable long id)
            throws IOException {
        return ResponseEntity.ok(productService.updateProduct(dto, id));
    }

}