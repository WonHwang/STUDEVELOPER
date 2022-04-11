package com.studeveloper.study.utils;

import com.studeveloper.study.domain.StudySchedule;
import com.studeveloper.study.dto.schedule.StudyScheduleResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class StudyScheduleConverter {
    public static StudyScheduleResponseDto toDto(StudySchedule studySchedule) {
        return new StudyScheduleResponseDto(studySchedule.getId(),
                studySchedule.getStudyDate(),studySchedule.getTitle(),studySchedule.getExplanation(),studySchedule.getStudy().getId(),
                studySchedule.getTasks());
    }

    public static List<StudyScheduleResponseDto> toDtos(List<StudySchedule> studySchedules) {
        return studySchedules.stream()
                .map(StudyScheduleConverter::toDto)
                .collect(Collectors.toList());
    }
}
