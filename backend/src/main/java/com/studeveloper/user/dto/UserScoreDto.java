package com.studeveloper.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel("UserScoreDto")
public class UserScoreDto {
    @ApiModelProperty(name = "유저id", example = "1")
    private Long userId;

    @ApiModelProperty(name = "유저 점수", example = "4.5")
    private double userScore;

    private UserScoreDto() {}
}
