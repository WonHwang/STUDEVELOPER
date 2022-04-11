package com.studeveloper.comment.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel("CommentRequestDto")
public class CommentRequestDto {
    @ApiModelProperty(name = "댓글 내용", example = "저도 참가하고 싶습니다!")
    private String content;
}
