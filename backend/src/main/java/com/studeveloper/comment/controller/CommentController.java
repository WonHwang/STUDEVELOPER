package com.studeveloper.comment.controller;

import com.studeveloper.comment.domain.Comment;
import com.studeveloper.comment.dto.CommentRequestDto;
import com.studeveloper.comment.dto.CommentResponseDto;
import com.studeveloper.comment.dto.CommentUpdateDto;
import com.studeveloper.comment.service.CommentService;
import com.studeveloper.comment.utils.CommentConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(value = "댓글 API", tags = {"Comment"})
@RestController
@RequestMapping("/api/v1/studies/{studyId}/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 작성
    @PostMapping
    @ApiOperation(value = "댓글 작성", notes = "게시글에 댓글을 작성한다.")
    public ResponseEntity<CommentResponseDto> create(
            @ApiIgnore Authentication authentication,
            @PathVariable Long studyId,
            @RequestBody @ApiParam(value = "댓글 정보", required = true) CommentRequestDto commentRequestDto) {
        return ResponseEntity.ok(commentService.save(authentication, studyId, commentRequestDto));
    }

    // 특정 스터디에 달린 댓글들 전체 조회
    @GetMapping
    @ApiOperation(value = "댓글 조회", notes = "게시글에 달린 댓글을 조회한다.")
    public ResponseEntity<List<CommentResponseDto>> readAllByStudy(@PathVariable Long studyId) {
        List<Comment> comments = commentService.findAllByStudyId(studyId);
        System.out.println(comments);
        return ResponseEntity.ok(CommentConverter.toDtos(comments));
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    @ApiOperation(value = "댓글 수정", notes = "본인이 작성한 댓글의 내용을 수정한다.")
    public ResponseEntity<CommentResponseDto> update(
            @ApiIgnore Authentication authentication,
            @PathVariable Long commentId,
            @RequestBody @ApiParam(value = "댓글 수정 정보", required = true) CommentUpdateDto commentUpdateDto) {
        return ResponseEntity.ok(commentService.update(authentication, commentId, commentUpdateDto));
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    @ApiOperation(value = "댓글 삭제", notes = "본인이 작성한 댓글을 삭제한다.")
    public ResponseEntity delete(
            @ApiIgnore Authentication authentication,
            @PathVariable Long commentId) {
        commentService.delete(authentication, commentId);
        return ResponseEntity.ok().build();
    }
}
