package com.studeveloper.study.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 사용자의 스터디 일정을 기록하는 모델 정의.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudySchedule {

    /**
     * 인스턴스 변수 영역
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="study_schedule_id")
    private Long id;

    @Column(nullable = false)
    private LocalDate studyDate;

    @Column(nullable = false)
    private String title;
    private String explanation;

    @ManyToOne
    @JoinColumn(name = "study_id")
    private Study study;

    @OneToMany(mappedBy = "studySchedule", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

    public StudySchedule(String title, String explanation, LocalDate studyDate, Study study) {
        this.title = title;
        this.explanation = explanation;
        this.studyDate = studyDate;
        this.study = study;
    }

}
