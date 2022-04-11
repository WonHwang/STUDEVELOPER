package com.studeveloper.study.utils;

import com.studeveloper.study.domain.Task;
import com.studeveloper.study.dto.task.TaskResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class TaskConverter {

    public static TaskResponseDto toDto(Task task) {
        TaskResponseDto responseDto = new TaskResponseDto(task.getId(), task.getPostUser().getName(), task.getUploadFileName(),task.getStoreFileName());
        return responseDto;
    }

    public static List<TaskResponseDto> toDtos(List<Task> tasks) {
        return tasks.stream()
                .map(TaskConverter::toDto)
                .collect(Collectors.toList());
    }
}
