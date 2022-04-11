package com.studeveloper.auth.controller;

import com.studeveloper.auth.dto.AuthRequestDto;
import com.studeveloper.auth.dto.AuthResponseDto;
import com.studeveloper.user.service.UserService;
import com.studeveloper.utils.BaseResponseBody;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 인증 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "인증 API", tags = {"Auth."})
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    public void empty() {
        return;
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "<strong>아이디와 패스워드</strong>를 통해 로그인 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = AuthResponseDto.class),
            @ApiResponse(code = 401, message = "유효하지 않은 패스워드", response = BaseResponseBody.class),
            @ApiResponse(code = 404, message = "아이디가 존재하지 않음", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<AuthResponseDto> login(@RequestBody @ApiParam(value="로그인 정보", required = true) AuthRequestDto requestDto) {
        AuthResponseDto responseDto = userService.login(requestDto);
        return ResponseEntity.status(200).body(responseDto);
    }
}
