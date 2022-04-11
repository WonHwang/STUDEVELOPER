package com.studeveloper.user.utils;

import com.studeveloper.study.domain.UserStudy;
import com.studeveloper.user.domain.User;
import com.studeveloper.user.dto.UserApplyResponseDto;
import com.studeveloper.user.dto.UserResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {
    private UserConverter() {}

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(user.getId(), user.getUserAuthId(), user.getName());
    }

    public static List<UserResponseDto> toDtos(List<User> users) {
        return users.stream()
                .map(UserConverter::toDto)
                .collect(Collectors.toList());
    }

    public static UserApplyResponseDto toApplyDto(User user, UserStudy userStudy){
        return new UserApplyResponseDto(user.getId(), user.getName(), user.getEvaluationCount(), user.getTotalScore(),
                userStudy.isAccepted(), userStudy.isApplied(), userStudy.isBlocked(), userStudy.getPenaltyMoney());
    }
}
