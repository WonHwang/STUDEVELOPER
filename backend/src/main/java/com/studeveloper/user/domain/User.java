package com.studeveloper.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.studeveloper.category.domain.Category;
import com.studeveloper.comment.domain.Comment;
import com.studeveloper.study.domain.Study;
import com.studeveloper.study.domain.UserStudy;
import com.studeveloper.user.dto.UserUpdateDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 유저 정보 모델 정의.
 */
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    /**
     * 인스턴스 변수 영역
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(nullable = false)
    @Email
    private String userAuthId;

    @Column(nullable = false)
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = false)
    private String name;

    private boolean isInfoOpened;
    private String gender;
    private String phoneNumber;
    private String githubUrl;
    private String blogUrl;
    private String baekjoonId;
    private String position;
    private String department;
    private double totalScore = 0;
    private int evaluationCount = 0;


    /**
     * 다른 테이블과 연결된 필드
     */
    // 작성한 댓글들
    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    // 관심있는 카테고리들
    @OneToMany(mappedBy = "user")
    private List<UserInterestCategory> interestCategories = new ArrayList<>();

    // 참여하는 스터디들
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserStudy> participatingStudies = new ArrayList<>();


    /**
     * getter 영역
     */


    /**
     * 로직 영역
     */

    public User (String userAuthId, String password, String name) {
        this.userAuthId = userAuthId;
        this.password = password;
        this.name = name;
    }

    public void update(UserUpdateDto userUpdateDto) {
        this.name = userUpdateDto.getName();
        this.position = userUpdateDto.getPosition();
        this.department = userUpdateDto.getDepartment();
    }

    public void isAllowedUser(User user) {
        if ( this.getId() != user.getId() ) {
            throw new IllegalStateException();
        }
    }

    public void updateScore(double score) {
        this.totalScore += score;
        this.evaluationCount ++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

}
