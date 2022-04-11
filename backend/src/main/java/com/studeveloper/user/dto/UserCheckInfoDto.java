package com.studeveloper.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel("UseCheckInfoDto")
public class UserCheckInfoDto {
    @ApiModelProperty(name = "유저 id", example = "1")
    private Long id;
    @ApiModelProperty(name = "유저 이름", example = "영석")
    private String name;

    private UserCheckInfoDto() {}

    public UserCheckInfoDto(Long userId, String name) {
        this.id = userId;
        this.name = name;
    }
}
