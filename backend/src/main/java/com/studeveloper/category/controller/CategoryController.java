package com.studeveloper.category.controller;

import com.studeveloper.category.domain.Category;
import com.studeveloper.category.dto.CategoryResponseDto;
import com.studeveloper.category.service.CategoryService;
import com.studeveloper.category.utils.CategoryConverter;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ApiOperation(value = "카테고리 목록 조회", notes = "전체 카테고리 목록을 조회한다.")
    public ResponseEntity<List<String>> readAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }
}
