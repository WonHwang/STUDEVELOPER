package com.studeveloper.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel("UserRequestDto")
public class UserRequestDto {
    @ApiModelProperty(name = "유저아이디", example = "test@gmail.com")
    private String userAuthId;

    @ApiModelProperty(name = "비밀번호", example = "dudtjr1234")
    private String password;

    @ApiModelProperty(name = "이름", example = "영석")
    private String name;

    private UserRequestDto() {}

}
