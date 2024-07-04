package com.example.ecommerce.restController;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAllProducts")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/getProductById/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductById(id);

        return product != null ? new ResponseEntity<>(product, HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/getProductByCategory/{id}")
    public ResponseEntity<List<ProductDto>> getProductByCategoryId(@PathVariable Long id) {
        List<ProductDto> productList = productService.getProductsByCategory(id);

        return productList != null ? new ResponseEntity<>(productList, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getProductByTagName/{tagName}")
    public ResponseEntity<List<ProductDto>> getProductByTag(@PathVariable String tagName) {
        List<ProductDto> product = productService.getProductsByTagName(tagName);

        return product != null ? new ResponseEntity<>(product, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getProductByHighestAverageRating")
    public ResponseEntity<List<ProductDto>> getProductByHighestAverageRating(@PathVariable Long id) {
        List<ProductDto> product = productService.getProductsByHighestAverageRating();

        return product != null ? new ResponseEntity<>(product, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/createProduct")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
        Product createdProduct = productService.createProduct(productDto);

        return createdProduct != null ? new ResponseEntity<>(createdProduct, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/updateProduct/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductDto product) {
        Product updatedProduct = productService.updateProduct(id, product);

        return updatedProduct != null ? new ResponseEntity<>(updatedProduct, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
