package com.example.usersm.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.usersm.Dto.ProductCacheDto;
import com.example.usersm.Entity.Product;
import com.example.usersm.Exception.ProductNotFoundException;
import com.example.usersm.Repository.ProductRepositry;

@Service
public class ProductCacheService {

    private final ProductRepositry productRepositry;

    public ProductCacheService(ProductRepositry productRepositry) {
        this.productRepositry = productRepositry;
    }

    @Cacheable(value = "products", key = "#id")
    public ProductCacheDto getProduct(long id) {

        System.out.println("Fetching Product From Database...");

        Product product = productRepositry.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product Not Found"));

        Long categoryId = null;
        String categoryName = null;

        if (product.getCategory() != null) {
            categoryId = product.getCategory().getId();
            categoryName = product.getCategory().getName();
        }

        return new ProductCacheDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getShort_description(),
                product.getImage_url(),
                product.getRating(),
                categoryId,
                categoryName
        );
    }
}