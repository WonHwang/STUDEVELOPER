package com.studeveloper.study.dto.schedule;

import com.studeveloper.study.domain.Task;
import com.studeveloper.study.dto.task.TaskResponseDto;
import com.studeveloper.study.utils.TaskConverter;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class StudyScheduleResponseDto {
    private Long id;
    private LocalDate studyDate;
    private String title;
    private String explanation;
    private Long studyId;
    private List<TaskResponseDto> tasks;

    public StudyScheduleResponseDto(Long id, LocalDate studyDate, String title, String explanation, Long studyId, List<Task> tasks) {
        this.id = id;
        this.studyDate = studyDate;
        this.title = title;
        this.explanation = explanation;
        this.studyId = studyId;
        this.tasks = TaskConverter.toDtos(tasks);
    }
}
