package com.studeveloper.category.domain;

import com.studeveloper.study.domain.Study;
import com.studeveloper.user.domain.UserInterestCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 카테고리 모델 정의.
 */
@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Long id;

    private String name;

    /**
     * 다른 테이블과 연결된 필드
     */
    // 스터디와의 중간 테이블로 연결
    @ManyToMany
    @JoinTable(name="study_category",
            joinColumns = @JoinColumn(name="category_id"),
            inverseJoinColumns = @JoinColumn(name = "study_id"))
    private List<Study> studies = new ArrayList<>();

    // 사용자 관심 카테고리 기록을 위해 중간 테이블로 연결
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<UserInterestCategory> userInterest = new ArrayList<>();

    //왜안됨??
    public void addStudy(Study study) {
        this.studies.add(study);
    }
}

