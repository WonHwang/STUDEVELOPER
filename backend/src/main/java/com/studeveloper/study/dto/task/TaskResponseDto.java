package com.studeveloper.study.dto.task;

import com.studeveloper.study.domain.Task;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class TaskResponseDto {

    private Long id;
    private String postUserName;
    private String uploadFileName;
    private String storeFileName;

    public TaskResponseDto(Long id, String postUserName, String uploadFileName, String storeFileName) {
        this.id = id;
        this.postUserName = postUserName;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
