package com.example.usersm.Dto;

public class ProductCacheDto {

    private Long id;
    private String name;
    private double price;
    private String description;
    private String shortDescription;
    private String imageUrl;
    private double rating;
    private Long categoryId;
    private String categoryName;

    public ProductCacheDto() {
    }

    public ProductCacheDto(Long id,
                           String name,
                           double price,
                           String description,
                           String shortDescription,
                           String imageUrl,
                           double rating,
                           Long categoryId,
                           String categoryName) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.shortDescription = shortDescription;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

  
}