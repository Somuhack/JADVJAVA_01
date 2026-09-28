package com.example.usersm.Dto;
// import com.example.usersm.Entity.Category;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

public class ProductRequestDto {

    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 100,message = "Name must be between 3 and 100 characters")
    private String name;

    @NotNull(message = "Product Price is required")
    @Positive(message = "Price Must be Posetive number")
    private double price;

    @NotBlank(message = "Product Description is required")
    @Size(min = 100, max = 1000,message = "Product Descriptions must be between 100 and 1000 characters")
    private String description;

    @NotBlank(message = "Product short_description is required")
    @Size(min = 10, max = 100,message = "Product short_description must be between 10 and 100 characters")
    private String short_description;
    
    @NotNull(message = "Product image_url is required")
    private MultipartFile image_url;

    @NotNull(message = "Product Rating is required")
    @Positive(message = "Product Rating Must be Posetive number")
    private double rating;

    @NotNull(message = "CategoryId Rating is required")
    @Positive(message = "Product CategoryId Must be Posetive number")
    private Long categoryId;
    
    
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
    public String getShort_description() {
        return short_description;
    }
    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }
    public MultipartFile getImage_url() {
        return image_url;
    }
    public void setImage_url(MultipartFile image_url) {
        this.image_url = image_url;
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
    
    
}