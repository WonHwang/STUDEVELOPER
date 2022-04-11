package com.studeveloper.comment.service;

import com.studeveloper.comment.domain.Comment;
import com.studeveloper.comment.domain.CommentRepository;
import com.studeveloper.comment.dto.CommentRequestDto;
import com.studeveloper.comment.dto.CommentResponseDto;
import com.studeveloper.comment.dto.CommentUpdateDto;
import com.studeveloper.comment.utils.CommentConverter;
import com.studeveloper.study.domain.Study;
import com.studeveloper.study.service.StudyService;
import com.studeveloper.user.domain.User;
import com.studeveloper.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final StudyService studyService;
    private final UserService userService;

    public CommentServiceImpl(CommentRepository commentRepository, StudyService studyService, UserService userService) {
        this.commentRepository = commentRepository;
        this.studyService = studyService;
        this.userService = userService;
    }

    @Override
    public CommentResponseDto save(Authentication authentication, Long studyId, CommentRequestDto commentRequestDto) {
        User user = userService.findRequestUser(authentication);
        Study study = studyService.findById(studyId);
        Comment comment = new Comment(commentRequestDto.getContent(), LocalDateTime.now(),study,user);
        commentRepository.save(comment);
        return CommentConverter.toDto(comment);
    }

    @Override
    public List<Comment> findAllByStudyId(Long studyId) {
        Study article = studyService.findById(studyId);
        return commentRepository.findAllByArticle(article)
                .orElseThrow(RuntimeException::new);

    }

    @Override
    public CommentResponseDto update(Authentication authentication, Long commentId, CommentUpdateDto commentUpdateDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(RuntimeException::new);
        User requestUser = userService.findRequestUser(authentication);
        comment.updateContent(requestUser, commentUpdateDto);
        return CommentConverter.toDto(comment);
    }

    @Override
    public void delete(Authentication authentication, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(RuntimeException::new);
        commentRepository.delete(comment);
    }
}
