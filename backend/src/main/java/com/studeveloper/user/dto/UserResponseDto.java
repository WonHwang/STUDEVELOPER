package com.studeveloper.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel("UserResDto")
public class UserResponseDto {

    @ApiModelProperty(name = "유저id", example = "1")
    private Long userId;

    @ApiModelProperty(name = "유저이메일아이디", example = "test@gmail.com")
    private String userAuthId;

    @ApiModelProperty(name = "유저이름", example = "영석")
    private String name;

    private  UserResponseDto() {}
    public UserResponseDto (Long userId, String userAuthId, String name) {
        this.userId = userId;
        this.userAuthId = userAuthId;
        this.name = name;
    }
}
