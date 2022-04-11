package com.studeveloper.auth.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

@Getter
@ApiModel("UserLoginPostRequest")
public class AuthResponseDto {
    private Long id;
    private String name;
    private String accessToken;
    public AuthResponseDto() {}
    public AuthResponseDto(Long userId, String name, String accessToken) {
        this.id = userId;
        this.name = name;
        this.accessToken = accessToken;
    }

}
