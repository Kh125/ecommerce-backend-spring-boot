package com.example.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Tag;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.TagRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    public ProductService(
        ProductRepository productRepository,
        CategoryRepository categoryRepository,
        TagRepository tagRepository,
        ModelMapper modelMapper
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
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

    public Product createProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);

        product.setTags(fetchTagList(productDto.getTagsName()));

        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, ProductDto updatedProduct) {
        return productRepository.findById(productId)
            .map(product -> {
                Optional.ofNullable(updatedProduct.getName()).ifPresent(product::setName);
                Optional.ofNullable(updatedProduct.getDescription()).ifPresent(product::setDescription);
                Optional.ofNullable(updatedProduct.getPrice()).ifPresent(product::setPrice);
                Optional.ofNullable(updatedProduct.getStock()).ifPresent(product::setStock);
                Optional.ofNullable(updatedProduct.getImageUrl()).ifPresent(product::setImageUrl);
                Optional.ofNullable(updatedProduct.getCategory()).ifPresent(category -> {
                    categoryRepository.findById(category.getId())
                                                .ifPresent(product::setCategory);
                });
                Optional.ofNullable(fetchTagList(updatedProduct.getTagsName())).ifPresent(product::setTags);
                return productRepository.save(product);
            })
            .orElse(null);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    private List<Tag> fetchTagList(List<String> tagNames) {
        List<Tag> tags = new ArrayList<>();

        tagNames.forEach(tagName -> {
            Tag tag = tagRepository.findByName(tagName);

            if (tag == null) {
                tag = new Tag();
                tag.setName(tagName);
                tagRepository.save(tag);
            }
            tags.add(tag);
        });

        return tags;
    }
}
