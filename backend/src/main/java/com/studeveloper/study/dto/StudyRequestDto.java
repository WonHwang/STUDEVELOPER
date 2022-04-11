package com.studeveloper.study.dto;

import com.studeveloper.category.domain.Category;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@ApiModel("StudyRequestDto")
public class StudyRequestDto {
        @ApiModelProperty(name = "제목", example = "파이썬 알고리즘 스터디")
        private String title;

        @ApiModelProperty(name = "스터디 설명", example = "저도 참가하고 싶습니다!")
        private String explanation;

        @ApiModelProperty(name = "지각 벌금", example = "2000")
        private int tardyPenaltyAmount;

        @ApiModelProperty(name = "결석 벌금", example = "3000")
        private int absentPenaltyAmount;

        @ApiModelProperty(name = "과제 미제출 벌금", example = "5000")
        private int assignmentPenaltyAmount;

        @ApiModelProperty(name = "모집 인원", example = "저도 참가하고 싶습니다!")
        private int targetUserNumber;

        @ApiModelProperty(name = "주 당 횟수", example = "2")
        private int timePerWeek;

        @ApiModelProperty(name = "횟수 당 시간", example = "3")
        private double hourPerTime;

        @ApiModelProperty(name = "썸네일 이미지 경로", example = "저도 참가하고 싶습니다!")
        private String imageUrl;

        @ApiModelProperty(name = "마감시한", example = "2021-08-18")
        private String deadline;

        @ApiModelProperty(name = "해당 카테고리", example = "[Python,Algorithm]")
        private List<String> categories;

        private StudyRequestDto() {}

}
