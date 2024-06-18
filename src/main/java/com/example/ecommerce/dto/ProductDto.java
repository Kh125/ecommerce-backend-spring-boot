package com.example.ecommerce.dto;

import java.util.List;

public class ProductDto {
    public ProductDto() {
        
    }

    private Long id;

    private String name;
    
    private String description;
    
    private Double price;
    
    private Integer stock;
    
    private List<String> imageUrl;

    private CategoryDto category;

    private List<TagDto> tags;

    private List<ReviewDto> reviews;
    
    // private Long categoryId;

    private List<String> tagsName;
    
    private List<Long> reviewsId;

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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public Integer getStock() {
        return stock;
    }
    
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    
    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    // public Long getCategoryId() {
    //     return categoryId;
    // }
    
    // public void setCategoryId(Long categoryId) {
    //     this.categoryId = categoryId;
    // }
    
    public List<String> getTagsName() {
        return tagsName;
    }
    
    public void setTagsName(List<String> tagsName) {
        this.tagsName = tagsName;
    }

    public List<Long> getReviewsId() {
        return reviewsId;
    }

    public void setReviewsId(List<Long> reviewsId) {
        this.reviewsId = reviewsId;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public List<TagDto> getTags() {
        return tags;
    }

    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }

    public List<ReviewDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDto> reviews) {
        this.reviews = reviews;
    }
    
}