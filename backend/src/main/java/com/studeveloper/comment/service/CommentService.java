package com.studeveloper.comment.service;

import com.studeveloper.comment.domain.Comment;
import com.studeveloper.comment.dto.CommentRequestDto;
import com.studeveloper.comment.dto.CommentResponseDto;
import com.studeveloper.comment.dto.CommentUpdateDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    CommentResponseDto save(Authentication authentication, Long studyId, CommentRequestDto commentRequestDto);
    List<Comment> findAllByStudyId(Long studyId);
    CommentResponseDto update(Authentication authentication, Long CommentId, CommentUpdateDto commentUpdateDto);
    void delete(Authentication authentication, Long CommentId);
}
