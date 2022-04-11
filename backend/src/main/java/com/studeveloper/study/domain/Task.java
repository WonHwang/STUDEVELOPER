package com.studeveloper.study.domain;

import com.studeveloper.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task {
    /**
     * 인스턴스 변수 영역
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="task_id")
    private Long id;

    private LocalDateTime registerTime;

    private String storeFileName;

    private String uploadFileName;


    public Task(String uploadFileName, String storeFileName) {
        this.storeFileName = storeFileName;
        this.uploadFileName = uploadFileName;
        this.registerTime = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "study_schedule_id")
    private StudySchedule studySchedule;

    @ManyToOne
    @JoinColumn(name = "post_user_id")
    private User postUser;

    public void setPostUser(User user) {
        this.postUser = user;
    }

    public void setRegisterTime() {
        this.registerTime = LocalDateTime.now();
    }

    public void setStudySchedule(StudySchedule schedule) {
        this.studySchedule = schedule;
    }
}
