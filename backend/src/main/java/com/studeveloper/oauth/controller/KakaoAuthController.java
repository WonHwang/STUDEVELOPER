package com.studeveloper.oauth.controller;

import com.studeveloper.user.dto.UserRequestDto;
import com.studeveloper.user.dto.UserResponseDto;
import com.studeveloper.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 카카오 로그인 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "카카오 로그인 API", tags = {"Kakao"})
@RestController
@RequestMapping("/api/v1/")
public class KakaoAuthController {
    private final UserService userService;

    public KakaoAuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ApiOperation(value = "회원 가입", notes = "회원가입을 한다.")
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto userRequestDto) {
        return  ResponseEntity.ok(userService.save(userRequestDto));
    }
}
