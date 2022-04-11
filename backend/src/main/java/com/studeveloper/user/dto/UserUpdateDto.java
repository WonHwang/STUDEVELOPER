package com.studeveloper.user.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

@Getter
@ApiModel("UserUpdateDto")
public class UserUpdateDto {
    private String name;
    private String position;
    private String department;

}
