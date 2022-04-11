package com.studeveloper.category.service;

import com.studeveloper.category.domain.Category;
import com.studeveloper.category.domain.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<String> findAll() {
        return categoryRepository.findAll().stream().map(Category::getName).collect(Collectors.toList());
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(IllegalAccessError::new);
    }
    //왜안됨??
}
