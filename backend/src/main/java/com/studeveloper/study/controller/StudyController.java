package com.studeveloper.study.controller;

import com.studeveloper.study.domain.Study;
import com.studeveloper.study.dto.*;
import com.studeveloper.study.dto.schedule.StudyScheduleRequestDto;
import com.studeveloper.study.dto.schedule.StudyScheduleResponseDto;
import com.studeveloper.study.utils.FileStore;
import com.studeveloper.study.utils.StudyConverter;
import com.studeveloper.study.service.StudyService;
import com.studeveloper.user.dto.UserApplyResponseDto;
import com.studeveloper.user.dto.UserResponseDto;
import com.studeveloper.user.utils.UserConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(value = "Study 모집 및 진행 API", tags = {"Study"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/studies")
public class StudyController {

    private final StudyService studyService;

    // 스터디 생성
    @PostMapping
    @ApiOperation(value = "스터디 모집글 생성", notes = "인증한 사용자를 방장으로 하는 스터디 생성")
    public ResponseEntity<StudyResponseDto> create(
            @ApiIgnore Authentication authentication,
            @RequestBody @ApiParam(value = "스터디 모집글 생성 정보", required = true) StudyRequestDto studyRequestDto) {
        Study study = studyService.save(authentication, studyRequestDto);
        return  ResponseEntity.ok(StudyConverter.toDto(study));
    }

    // 스터디 전체 목록 조회
    @GetMapping
    @ApiOperation(value = "스터디 목록 조회", notes = "전체 스터디 목록을 조회한다.")
    public ResponseEntity<List<StudyResponseDto>> readAll() {
        List<Study> studies = studyService.findAll();
        return ResponseEntity.ok(StudyConverter.toDtos(studies));
    }

    // 단일 스터디 조회
    @GetMapping("/{studyId}")
    @ApiOperation(value = "스터디 상세 조회", notes = "스터디의 상세 정보를 조회한다.")
    public ResponseEntity<StudyResponseDto> readDetail(@PathVariable Long studyId) {
        Study study = studyService.findById(studyId);
        return ResponseEntity.ok(StudyConverter.toDto(study));
    }

    // 스터디 수정
    @PutMapping("/{studyId}")
    @ApiOperation(value = "스터디 수정", notes = "스터디 정보를 수정한다.")
    public ResponseEntity<StudyResponseDto> update(
            @ApiIgnore Authentication authentication,
            @PathVariable Long studyId,
            @RequestBody @ApiParam(value = "모집글 수정 정보", required = true) StudyUpdateDto studyUpdateDto) {
        Study study = studyService.update(authentication, studyId, studyUpdateDto);
        return ResponseEntity.ok(StudyConverter.toDto(study));
    }

    // 스터디 삭제
    @DeleteMapping("/{studyId}")
    @ApiOperation(value = "스터디 삭제", notes = "본인이 방장인 스터디를 삭제한다.")
    public ResponseEntity delete(
            @ApiIgnore Authentication authentication,
            @PathVariable Long studyId) {
        studyService.delete(authentication, studyId);
        return ResponseEntity.ok().build();
    }

    // 스터디 지원
    @PostMapping("/{studyId}/apply")
    @ApiOperation(value = "스터디 지원", notes = "스터디를 신청한다.")
    public ResponseEntity apply(
            @ApiIgnore Authentication authentication,
            @PathVariable Long studyId) {
        studyService.apply(authentication, studyId);
        return ResponseEntity.ok().build();
    }

    // 스터디 지원 취소
    @PostMapping("/{studyId}/cancel-apply")
    @ApiOperation(value = "스터디 지원 취소", notes = "스터디 지원을 취소한다.")
    public ResponseEntity cancelApply(
            @ApiIgnore Authentication authentication,
            @PathVariable Long studyId) {
        studyService.cancelApply(authentication, studyId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{studyId}/deny-apply/users/{userId}")
    @ApiOperation(value = "스터디 지원 거절", notes = "스터디 지원을 거절한다.")
    public ResponseEntity denyApply(
            @ApiIgnore Authentication authentication,
            @PathVariable Long studyId,
            @PathVariable Long userId) {
        studyService.denyApply(authentication, studyId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{studyId}/accept-apply/users/{userId}")
    @ApiOperation(value = "스터디 지원 수락", notes = "스터디 지원을 수락한다.")
    public ResponseEntity acceptApply(
            @ApiIgnore Authentication authentication,
            @PathVariable Long studyId,
            @PathVariable Long userId) {
        studyService.acceptApply(authentication, studyId, userId);
        return ResponseEntity.ok().build();
    }

    // 스터디의 화상 회의를 열거나 닫는다
    @PostMapping("/{studyId}/session/{isOpen}")
    @ApiOperation(value = "스터디 화상 회의 관리", notes = "스터디 화상 회의를 열거나 닫는다.")
    public ResponseEntity openAndCloseConference(
            @ApiIgnore Authentication authentication,
            @PathVariable Long studyId,
            @PathVariable Boolean isOpen) {
        studyService.openAndCloseConference(authentication, studyId, isOpen);
        return ResponseEntity.ok().build();
    }

    // 유저가 참여중인 스터디 목록을 가져온다
    @GetMapping("/{userId}/participation")
    @ApiOperation(value = "참여 중인 스터디 목록 조회", notes = "유저가 참여중인 스터디 목록을 조회한다.")
    public ResponseEntity<List<StudyResponseDto>> getStudyListParticipating(
            @ApiIgnore Authentication authentication,
            @PathVariable Long userId) {
        List<Study> studies = studyService.getStudyListParticipating(authentication, userId);
        return ResponseEntity.ok(StudyConverter.toDtos(studies));
    }

    // 자신이 스터디장인 스터디 목록을 가져온다
    @GetMapping("/{userId}/head")
    @ApiOperation(value = "자신이 스터디장인 스터디 목록 조회", notes = "자신이 스터디장인 스터디 목록을 조회한다.")
    public ResponseEntity<List<StudyResponseDto>> getStudyListHead(
            @ApiIgnore Authentication authentication,
            @PathVariable Long userId) {
        List<Study> studies = studyService.getStudyListHead(authentication, userId);
        return ResponseEntity.ok(StudyConverter.toDtos(studies));
    }

    // 참여 중이면서 화상 회의가 열린 스터디 목록을 가져온다
    @GetMapping("/{userId}/open-study")
    @ApiOperation(value = "참여 중이면서 화상 회의가 열린 스터디 목록 조회", notes = "유저가 참여 중이면서 화상 회의가 열린 스터디 목록을 조회한다.")
    public ResponseEntity<List<StudyResponseDto>> getStudyListOpen(
            @ApiIgnore Authentication authentication,
            @PathVariable Long userId) {
        List<Study> studies = studyService.getStudyListOpen(authentication, userId);
        return ResponseEntity.ok(StudyConverter.toDtos(studies));
    }

    @PostMapping("/{studyId}/study-schedule")
    @ApiOperation(value = "스터디 일정 등록", notes = "해당 날짜의 스터디 일정을 등록함")
    public ResponseEntity registerSchedule(
            @ApiIgnore Authentication authentication,
            @PathVariable Long studyId,
            @RequestBody @ApiParam(value = "스터디 일정 등록", required = true) StudyScheduleRequestDto studyScheduleInfo) {
        studyService.registerSchedule(authentication, studyId, studyScheduleInfo);
        return  ResponseEntity.ok().build();
    }

    @GetMapping("/{studyId}/study-schedule")
    @ApiOperation(value = "스터디 일정 조회", notes = "해당 스터디의 스터디 일정을 조회한다.")
    public ResponseEntity<List<StudyScheduleResponseDto>> readSchedule(
            @PathVariable Long studyId) {
        return  ResponseEntity.ok(studyService.getSchedule(studyId));
    }

    @GetMapping("/{studyId}/apply")
    @ApiOperation(value = "스터디에 신청한 유저 조회", notes = "해당 스터디에 신청한 유저 정보를 반환한다.")
    public ResponseEntity<List<UserApplyResponseDto>> readApplyUsers(
            @PathVariable Long studyId) {
        return ResponseEntity.ok(studyService.getApplyUsers(studyId));
    }

    // 스터디 검색
    @GetMapping("/search/{title}/{sort}")
    @ApiOperation(value = "설정한 조건으로 스터디 검색", notes = "검색된 스터디 정보를 반환한다.")
    public ResponseEntity<List<StudyResponseDto>> readSearchStudy(@PathVariable String title, @PathVariable String sort) {
        List<Study> studies = studyService.searchStudy(title, sort);
        return ResponseEntity.ok(StudyConverter.toDtos(studies));
    }
}
