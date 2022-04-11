package com.studeveloper.comment.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@ApiModel("CommentRequestDto")
public class CommentResponseDto {
    @ApiModelProperty(name = "댓글 id", example = "1")
    private Long id;

    @ApiModelProperty(name = "댓글 내용", example = "좋아요!")
    private String content;

    @ApiModelProperty(name = "작성 시간", example = "작성 시간")
    private LocalDateTime createTime;

    @ApiModelProperty(name = "작성자", example = "작성자")
    private String authorName;

    public CommentResponseDto(Long id, String content, LocalDateTime createTime, String authorName) {
        this.id = id;
        this.content = content;
        this.createTime = createTime;
        this.authorName = authorName;
    }
}
