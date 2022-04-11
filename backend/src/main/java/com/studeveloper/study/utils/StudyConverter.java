package com.studeveloper.study.utils;

import com.studeveloper.category.domain.Category;
import com.studeveloper.comment.utils.CommentConverter;
import com.studeveloper.study.domain.Study;
import com.studeveloper.study.dto.StudyResponseDto;
import com.studeveloper.user.utils.UserConverter;

import java.util.List;
import java.util.stream.Collectors;

public class StudyConverter {
    private StudyConverter() {}

    public static StudyResponseDto toDto(Study study) {
        StudyResponseDto responseDto = new StudyResponseDto(study.getId(), study.getTitle(), study.getExplanation(), study.getTimePerWeek(), study.getHourPerTime(), study.getTardyPenaltyAmount(),
                study.getAbsentPenaltyAmount(), study.getAssignmentPenaltyAmount(), study.getTargetUserNumber(), study.getParticipatingUsers().size(),
                study.isRecruitClosed(), study.isStudyClosed(), study.getImageUrl(), study.getCreateTime(), study.getDeadline(),study.getCategories().stream().map(Category::getName).collect(Collectors.toList()),
                UserConverter.toDto(study.getOwner()), UserConverter.toDtos(study.getParticipatingUsers()),CommentConverter.toDtos(study.getComments()), study.isConferenceOpened());
        return responseDto;
    }

    public static List<StudyResponseDto> toDtos(List<Study> studies) {
        return studies.stream()
                .map(StudyConverter::toDto)
                .collect(Collectors.toList());
    }
}
