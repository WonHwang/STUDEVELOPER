package com.studeveloper.comment.utils;

import com.studeveloper.comment.domain.Comment;
import com.studeveloper.comment.dto.CommentResponseDto;
import com.studeveloper.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;

public class CommentConverter {
    private CommentConverter() {}

    public static CommentResponseDto toDto(Comment comment) {
        CommentResponseDto responseDto = new CommentResponseDto(comment.getId(), comment.getContent(), comment.getCreateTime(), comment.getWriter().getName());
        return responseDto;
    }

    public static List<CommentResponseDto> toDtos(List<Comment> comments) {
        return comments.stream()
                .map(CommentConverter::toDto)
                .collect(Collectors.toList());
    }
}
