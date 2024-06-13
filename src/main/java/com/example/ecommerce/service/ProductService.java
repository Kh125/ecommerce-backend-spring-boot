package com.example.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;

import com.example.ecommerce.util.ProductUtil;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> getProductsByTagName(String tagName) {
        return productRepository.findByTags_Name(tagName);
    }

    public List<Product> getProductsByHighestAverageRating() {
        return productRepository.findByHighestAverageRating();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, Product updatedProduct) {
        return productRepository.findById(productId)
            .map(product -> {
                if(updatedProduct.getName() != null) {
                    product.setName(updatedProduct.getName());
                }
                if(updatedProduct.getDescription() != null) {
                    product.setDescription(updatedProduct.getDescription());
                }
                if(updatedProduct.getPrice() != null) {
                    product.setPrice(updatedProduct.getPrice());
                }
                if(updatedProduct.getStock() != null) {
                    product.setStock(updatedProduct.getStock());
                }

                return productRepository.save(product);
            })
            .orElse(null);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
