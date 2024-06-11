package com.example.ecommerce.restController;

import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.service.ProductService;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
}
