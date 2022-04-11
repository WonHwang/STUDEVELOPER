package com.studeveloper.comment.domain;

import com.studeveloper.comment.dto.CommentUpdateDto;
import com.studeveloper.study.domain.Study;
import com.studeveloper.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 댓글 모델 정의.
 */
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long id;

    private String content;
    private LocalDateTime createTime;

    public Comment(String content, LocalDateTime createTime, Study article, User writer) {
        this.content = content;
        this.createTime = createTime;
        this.article = article;
        this.writer = writer;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", article=" + article +
                ", writer=" + writer +
                '}';
    }

    /**
     * 다른 테이블과 연결된 필드
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    public void updateContent(User requestUser, CommentUpdateDto commentUpdateDto) {
        checkWriter(requestUser);
        this.content = commentUpdateDto.getContent();
    }

    public void checkWriter(User requestUser) {
        if (!this.writer.equals(requestUser)) {
            // 사용자 지정 error 정해주기
            throw new IllegalStateException();
        }
    }

}