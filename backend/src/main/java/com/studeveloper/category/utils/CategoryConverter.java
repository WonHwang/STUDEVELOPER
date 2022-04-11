package com.studeveloper.category.utils;

import com.studeveloper.category.domain.Category;
import com.studeveloper.category.domain.CategoryRepository;
import com.studeveloper.category.dto.CategoryResponseDto;
import com.studeveloper.comment.domain.Comment;
import com.studeveloper.comment.dto.CommentResponseDto;
import com.studeveloper.comment.utils.CommentConverter;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryConverter {
    private CategoryConverter() {}

    public static CategoryResponseDto toDto(Category category) {
        CategoryResponseDto responseDto = new CategoryResponseDto(category.getName());
        return responseDto;
    }

    public static List<CategoryResponseDto> toDtos(List<Category> categories) {
        return categories.stream()
                .map(CategoryConverter::toDto)
                .collect(Collectors.toList());
    }
}
