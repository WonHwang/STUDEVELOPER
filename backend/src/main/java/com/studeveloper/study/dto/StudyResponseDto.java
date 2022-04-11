package com.studeveloper.study.dto;

import com.studeveloper.category.domain.Category;
import com.studeveloper.comment.domain.Comment;
import com.studeveloper.comment.dto.CommentResponseDto;
import com.studeveloper.user.domain.User;
import com.studeveloper.user.dto.UserResponseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@ApiModel("StudyResponse")
public class StudyResponseDto {
    @ApiModelProperty(name="스터디 id", example = "Long")
    private Long studyId;

    @ApiModelProperty(name="제목", example = "String")
    private String title;

    @ApiModelProperty(name = "설명", example = "String")
    private String explanation;

    @ApiModelProperty(name = "지각벌금", example = "3000")
    private int tardyPenaltyAmount;

    @ApiModelProperty(name = "결석벌금", example = "4000")
    private int absentPenaltyAmount;

    @ApiModelProperty(name = "과제 미제출 벌금", example = "6000")
    private int assignmentPenaltyAmount;

    @ApiModelProperty(name = "모집인원", example = "4")
    private int targetUserNumber;

    @ApiModelProperty(name = "participantUserNumber", example = "3")
    private int participantUserNumber;

    @ApiModelProperty(name = "참가한 사람 정보", example = "참가한 사람 정보")
    private List<UserResponseDto> participantUser;

    @ApiModelProperty(name = "모집마감여부", example = "false")
    private boolean isRecruitClosed;

    @ApiModelProperty(name = "스터디종료여부", example = "false")
    private boolean isStudyClosed;

    @ApiModelProperty(name = "썸네일 이미지 경로", example = "'{prefix}/Python.jpg'")
    private String imageUrl;

    @ApiModelProperty(name = "작성 일자", example = "2021-08-13")
    private LocalDate createTime;

    @ApiModelProperty(name = "모집 마감 일자", example = "2021-08-17")
    private LocalDate deadline;

    @ApiModelProperty(name = "스터디 카테고리", example = "[Python,Algorithm]")
    private List<String> categories;


    @ApiModelProperty(name = "스터디 카테고리", example = "[Python,Algorithm]")
    private List<CommentResponseDto> comments;

    @ApiModelProperty(name = "스터디장 정보", example = "1")
    private UserResponseDto userInfo;

    @ApiModelProperty(name = "화상 회의가 열린 여부", example = "true")
    private boolean isConferenceOpened;

    private int timePerWeek;
    private double hourPerTime;


    public StudyResponseDto(
            Long studyId, String title, String explanation, int timePerWeek, double hourPerTime, int tardyPenaltyAmount, int absentPenaltyAmount, int assignmentPenaltyAmount, int targetUserNumber,
            int participantUserNumber, boolean isRecruitClosed, boolean isStudyClosed, String imageUrl, LocalDate createTime,
            LocalDate deadline, List<String> categories, UserResponseDto userInfo, List<UserResponseDto> participantUserInfos, List<CommentResponseDto> comments, boolean isConferenceOpened) {
        this.studyId = studyId;
        this.title = title;
        this.explanation = explanation;
        this.timePerWeek = timePerWeek;
        this.hourPerTime = hourPerTime;
        this.tardyPenaltyAmount = tardyPenaltyAmount;
        this.absentPenaltyAmount = absentPenaltyAmount;
        this.assignmentPenaltyAmount = assignmentPenaltyAmount;
        this.targetUserNumber = targetUserNumber;
        this.participantUserNumber = participantUserNumber;
        this.isRecruitClosed = isRecruitClosed;
        this.isStudyClosed = isStudyClosed;
        this.imageUrl = imageUrl;
        this.createTime = createTime;
        this.deadline = deadline;
        this.categories = categories;
        this.userInfo = userInfo;
        this.participantUser = participantUserInfos;
        this.comments = comments;
        this.isConferenceOpened = isConferenceOpened;
    }

}
