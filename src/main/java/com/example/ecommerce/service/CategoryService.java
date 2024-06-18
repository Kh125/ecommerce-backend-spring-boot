package com.example.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.CategoryDto;
import com.example.ecommerce.model.Category;
import com.example.ecommerce.repository.CategoryRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryService(
        CategoryRepository categoryRepository,
        ModelMapper modelMapper
    ) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
            .stream()
            .map(category -> modelMapper.map(category, CategoryDto.class))
            .collect(Collectors.toList());
    }

    public CategoryDto getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));
    }

    public Category createCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long categoryId, CategoryDto categoryDto) {
        if (categoryDto == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));
        
        existingCategory.setName(categoryDto.getName());
        existingCategory.setDescription(categoryDto.getDescription());
        
        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
