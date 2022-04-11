package com.studeveloper.category.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel("CategoryResponseDto")
public class CategoryResponseDto {

    @ApiModelProperty(name = "카테고리 이름", example = "Python")
    private String name;

    private CategoryResponseDto() {}
    public CategoryResponseDto (String name) {
        this.name = name;
    }
}
