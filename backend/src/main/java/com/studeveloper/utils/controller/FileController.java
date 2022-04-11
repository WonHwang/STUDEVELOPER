package com.studeveloper.utils.controller;

import com.studeveloper.study.service.StudyService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@Api(value = "파일 다운로드 API", tags = {"Task"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attach")
public class FileController {
    private final StudyService studyService;

    @GetMapping("/{taskId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long taskId) throws MalformedURLException {
        String uploadFileName = studyService.getUploadFileName(taskId);
        UrlResource resource = studyService.getUrlResource(taskId);
        String contentDisposition = "attachment; filename=\"" + uploadFileName + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}
