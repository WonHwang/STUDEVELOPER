package com.studeveloper.study.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;

@Getter
@ApiModel("StudySearchDto")
public class StudySearchDto {
        @ApiModelProperty(name = "검색할 단어", example = "알고리즘")
        private String search;

        @ApiModelProperty(name = "검색할 카테고리", example = "[Python,Algorithm]")
        private List<String> categories;

        @ApiModelProperty(name = "정렬 기준", example = "최신순")
        private String sort;

        private StudySearchDto() {}
}
