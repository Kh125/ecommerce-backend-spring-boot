package com.example.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public ProductService(
        ProductRepository productRepository,
        CategoryRepository categoryRepository,
        ModelMapper modelMapper
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
            .stream()
            .map(product -> modelMapper.map(product, ProductDto.class))
            .collect(Collectors.toList());
    }

    public ProductDto getProductById(Long productId) {
        return productRepository.findById(productId)
            .map(product -> modelMapper.map(product, ProductDto.class))
            .orElse(null);
    }

    public List<ProductDto> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId)
            .stream()
            .map(product -> modelMapper.map(product, ProductDto.class))
            .collect(Collectors.toList());
    }

    public List<ProductDto> getProductsByTagName(String tagName) {
        return productRepository.findByTags_Name(tagName)
            .stream()
            .map(product -> modelMapper.map(product, ProductDto.class))
            .collect(Collectors.toList());
    }

    public List<ProductDto> getProductsByHighestAverageRating() {
        return productRepository.findByHighestAverageRating()
            .stream()
            .map(product -> modelMapper.map(product, ProductDto.class))
            .collect(Collectors.toList());
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
