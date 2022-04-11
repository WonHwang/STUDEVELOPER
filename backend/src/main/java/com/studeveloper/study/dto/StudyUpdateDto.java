package com.studeveloper.study.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class StudyUpdateDto {
        private String title;
        private String explanation;
        private String rule;
        private int targetUserNumber;
        private String imageUrl;
        private LocalDate deadline;
}
