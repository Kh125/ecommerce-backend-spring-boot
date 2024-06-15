package com.example.ecommerce.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(
        ProductRepository productRepository,
        CategoryRepository categoryRepository
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
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
                if(updatedProduct.getCategory() != null) {
                    Category category = categoryRepository.findById(updatedProduct.getCategory().getId()).orElseThrow(() -> new RuntimeException("Category Not found"));
                    product.setCategory(category);
                }

                return productRepository.save(product);
            })
            .orElse(null);
    }

    public Product updateProductCategoryByCategoryId(Long categoryId, Long productId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category Not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product Not found"));
        product.setCategory(category);
        return productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
