package com.example.usersm.Dto;
import org.springframework.hateoas.RepresentationModel;
public class ProductResponseDto extends RepresentationModel<ProductResponseDto>{

    private Long id;
    private String name;
    private double price;
    private String description;
    private String short_description;
    private String image_url;
    private double rating;
    private Long categoryId;    // 🟢 Changed from Category to Long
    private String categoryName ;
  public ProductResponseDto() {
}
    public ProductResponseDto(Long id, String name, double price, String description, String short_description,
            String image_url, double rating, Long categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.short_description = short_description;
        this.image_url = image_url;
        this.rating = rating;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public String getDescription() {
        return description;
    }
    public String getShort_description() {
        return short_description;
    }
    public String getImage_url() {
        return image_url;
    }
    public double getRating() {
        return rating;
    }
    public Long getCategoryId() {
        return categoryId;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setId(Long id) {
    this.id = id;
}

public void setName(String name) {
    this.name = name;
}

public void setPrice(double price) {
    this.price = price;
}

public void setDescription(String description) {
    this.description = description;
}

public void setShort_description(String short_description) {
    this.short_description = short_description;
}

public void setImage_url(String image_url) {
    this.image_url = image_url;
}

public void setRating(double rating) {
    this.rating = rating;
}

public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
}

public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
}


}