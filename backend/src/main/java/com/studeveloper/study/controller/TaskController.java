package com.studeveloper.study.controller;

import com.studeveloper.study.dto.task.TaskRequestDto;
import com.studeveloper.study.dto.task.TaskResponseDto;
import com.studeveloper.study.service.StudyService;
import com.studeveloper.study.utils.FileStore;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.List;

@Api(value = "과제물 관련 API", tags = {"Task"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/study-schedule")
public class TaskController {

    private final StudyService studyService;

    @PostMapping("/{studyScheduleId}")
    public ResponseEntity registerTask(
            @ApiIgnore Authentication authentication,
            @PathVariable Long studyScheduleId,
            @ModelAttribute TaskRequestDto taskInfo) {
        try {
            studyService.registerTask(authentication, studyScheduleId, taskInfo);
        } catch (IOException e) {
            throw new IllegalStateException();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{studyScheduleId}")
    public ResponseEntity<List<TaskResponseDto>> getTasks(@PathVariable Long studyScheduleId) {
        return ResponseEntity.ok(studyService.getTasks(studyScheduleId));
    }
}
