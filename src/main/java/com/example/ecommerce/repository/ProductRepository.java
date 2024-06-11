package com.example.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ecommerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    
    List<Product> findByCategoryId(Long categoryId);
    
    List<Product> findByTags_Name(String tagName);

    @Query("Select p from Product p Left Join p.reviews r Group By p Order By AVG(r.rating) DESC")
    List<Product> findByHighestAverageRating();
}
