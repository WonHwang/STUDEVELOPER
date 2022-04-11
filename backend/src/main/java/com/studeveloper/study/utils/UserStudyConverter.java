package com.studeveloper.study.utils;

import com.studeveloper.study.domain.Study;
import com.studeveloper.study.domain.UserStudy;
import com.studeveloper.study.dto.StudyResponseDto;
import com.studeveloper.study.dto.UserStudyResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserStudyConverter {
    private UserStudyConverter() {}

    public static UserStudyResponseDto toDto(UserStudy userStudy) {
        UserStudyResponseDto responseDto = new UserStudyResponseDto();
        return responseDto;
    }

    public static List<UserStudyResponseDto> toDtos(List<UserStudy> userStudies) {
        return userStudies.stream()
                .map(UserStudyConverter::toDto)
                .collect(Collectors.toList());
    }
}
