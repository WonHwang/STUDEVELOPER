package com.studeveloper.study.dto.task;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TaskRequestDto {
    private MultipartFile taskFile;
}
