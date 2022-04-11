package com.studeveloper.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel("UserApplyResponseDto")
public class UserApplyResponseDto {

    @ApiModelProperty(name = "유저id", example = "1")
    private Long userId;

    @ApiModelProperty(name = "유저이름", example = "영석")
    private String name;

    @ApiModelProperty(name = "스터디 참가 횟수", example = "5")
    private int evaluationCount;

    @ApiModelProperty(name = "유저 총합 점수", example = "72.5")
    private double totalScore;

    @ApiModelProperty(name = "스터디 참여 여부", example = "true")
    private boolean isAccepted;

    @ApiModelProperty(name = "스터디 신청 여부", example = "true")
    private boolean isApplied;

    @ApiModelProperty(name = "스터디 블락 여부", example = "true")
    private boolean isBlocked;

    @ApiModelProperty(name = "벌금", example = "5000")
    private int penaltyMoney;


    private UserApplyResponseDto() {
    }

    public UserApplyResponseDto(Long userId, String name, int evaluationCount, double totalScore,
                                boolean isAccepted, boolean isApplied, boolean isBlocked, int penaltyMoney
    ) {
        this.userId = userId;
        this.name = name;
        this.evaluationCount = evaluationCount;
        this.totalScore = totalScore;
        this.isAccepted = isAccepted;
        this.isApplied = isApplied;
        this.isBlocked = isBlocked;
        this.penaltyMoney = penaltyMoney;
    }
}
