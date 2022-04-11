package com.studeveloper.study.domain;

import com.studeveloper.study.domain.Study;
import com.studeveloper.study.domain.StudySchedule;
import com.studeveloper.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 유저 스터디 연결 모델 정의.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserStudy {

    /**
     * 인스턴스 변수 영역
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_study_id")
    private Long id;

    private int penaltyMoney = 0;
    private boolean isApplied = true;
    private boolean isAccepted = false;
    private boolean isBlocked = false;

    public UserStudy(User user, Study study) {
        this.user = user;
        this.study = study;
    }

    public void apply() {
        if (this.isApplied) {
            throw new IllegalStateException("이미 신청한 스터디입니다.");
        } else if (this.isBlocked) {
            throw new IllegalStateException("이미 요청이 처리된 스터디입니다.");
        }
        this.isApplied = true;
        this.study.getApplyingUsers().add(this);
    }

    public void create() {
        this.isApplied = true;
        this.isAccepted = true;
        this.isBlocked = true;
        this.study.getApplyingUsers().add(this);
    }

    public void accept() {
        if (this.isBlocked) {
            throw new IllegalStateException("이미 처리한 요청입니다.");
        }
        this.isAccepted = true;
        this.isBlocked = true;
        this.study.checkMemberNumber();
    }

    public void deny() {
        if (this.isBlocked) {
            throw new IllegalStateException("이미 처리한 요청입니다.");
        }
        this.isBlocked = true;
    }


    /**
     * 다른 테이블과 연결된 필드
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
