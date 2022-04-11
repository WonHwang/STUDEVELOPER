package com.studeveloper.user.controller;

import com.studeveloper.category.domain.Category;
import com.studeveloper.category.dto.CategoryResponseDto;
import com.studeveloper.category.utils.CategoryConverter;
import com.studeveloper.study.domain.Study;
import com.studeveloper.study.dto.StudyResponseDto;
import com.studeveloper.study.utils.StudyConverter;
import com.studeveloper.user.domain.User;
import com.studeveloper.user.dto.*;
import com.studeveloper.user.service.UserService;
import com.studeveloper.user.utils.UserConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {this.userService = userService; }

    // 회원가입
    @PostMapping
    @ApiOperation(value = "회원 가입", notes = "회원가입을 한다.")
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto userRequestDto) {
        return  ResponseEntity.ok(userService.save(userRequestDto));
    }

    @GetMapping("/check-info")
    @ApiOperation(value = "id,이름 확인", notes = "로그인한 유저의 id와 이름을 돌려준다")
    public ResponseEntity<UserCheckInfoDto> checkInfo(@ApiIgnore Authentication authentication) {
        return ResponseEntity.ok(userService.checkInfo(authentication));
    }

    // 유저 정보 조회
    @GetMapping("/{userId}")
    @ApiOperation(value = "회원 정보 조회", notes = "회원 개인 정보를 조회한다.")
    public ResponseEntity<UserResponseDto> readDetail(@PathVariable Long userId) {
        User user = userService.findById(userId);
        return ResponseEntity.ok(UserConverter.toDto(user));
    }

    // 유저 정보 수정
    @PutMapping("/{userId}")
    @ApiOperation(value = "회원 정보 수정", notes = "회원 개인 정보를 수정한다.")
    public ResponseEntity<UserResponseDto> update(@ApiIgnore Authentication authentication, @PathVariable Long userId, UserUpdateDto userUpdateDto) {
        return ResponseEntity.ok(userService.update(authentication, userId, userUpdateDto));
    }

    // 유저 정보 삭제
    @DeleteMapping("/{userId}")
    @ApiOperation(value = "회원 삭제", notes = "회원 개인 정보를 삭제한다.")
    public ResponseEntity delete(@ApiIgnore Authentication authentication, @PathVariable Long userId) {
        userService.delete(authentication, userId);
        return ResponseEntity.ok().build();
    }


    // 참여중인 스터디 조회
    @GetMapping("/{userId}/participating-study")
    @ApiOperation(value = "참여중인 스터디 조회", notes = "참여중인 스터디의 목록을 조회한다.")
    public  ResponseEntity<List<StudyResponseDto>> readParticipatingStudies(@PathVariable Long userId) {
        List<Study> participatingStudies = userService.findParticipatingStudy(userId);
        return ResponseEntity.ok(StudyConverter.toDtos(participatingStudies));
    }

    // 관심 카테고리 조회
    @GetMapping("/{userId}/interest-categories")
    @ApiOperation(value = "관심 카테고리 조회", notes = "유저의 관심 카테고리를 조회한다.")
    public  ResponseEntity<List<CategoryResponseDto>> readInterestCategories(@PathVariable Long userId) {
        List<Category> interestCategories = userService.findInterestCategories(userId);
        return ResponseEntity.ok(CategoryConverter.toDtos(interestCategories));
    }

    // 유저 점수 업데이트
    @PostMapping("/score")
    @ApiOperation(value = "유저 평점 갱신", notes = "유저의 평점을 갱신한다.")
    public ResponseEntity updateUserScore(@RequestBody List<UserScoreDto> userScoreDtoList) {
        userService.updateScores(userScoreDtoList);
        return ResponseEntity.ok().build();
    }
}
