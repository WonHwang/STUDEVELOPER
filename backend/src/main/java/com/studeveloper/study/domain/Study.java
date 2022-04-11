package com.studeveloper.study.domain;

import com.studeveloper.category.domain.Category;
import com.studeveloper.comment.domain.Comment;
import com.studeveloper.study.dto.StudyUpdateDto;
import com.studeveloper.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 스터디 모델 정의.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study {

    /**
     * 인스턴스 변수 영역
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="study_id")
    private Long id;
    private String title;
    @Lob
    private String explanation;
    // 스터디 벌금 규칙
    private int tardyPenaltyAmount;
    private int absentPenaltyAmount;
    private int assignmentPenaltyAmount;
    private int targetUserNumber;
    private int timePerWeek;
    private double hourPerTime;

    private String imageUrl = "";
    private LocalDate createTime;
    private LocalDate deadline;
    private boolean isRecruitClosed = false;
    private boolean isStudyClosed = false;
    private boolean isConferenceOpened = false;

    /**
     * 다른 테이블과 연결된 필드
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    //게시글에 달린 댓글들
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    //스터디의 카테고리 분류 StudyCategory
    @ManyToMany(mappedBy = "studies")
    private List<Category> categories = new ArrayList<>();

    //스터디 참가자들과 UserStudy
    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<UserStudy> applyingUsers = new ArrayList<>();


    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<StudySchedule> studySchedules = new ArrayList<>();


    public List<User> getParticipatingUsers() {
        return applyingUsers.stream()
                .filter(UserStudy::isAccepted)
                .map(UserStudy::getUser)
                .collect(Collectors.toList());
    }

    public void addCategory(Category category) {
        this.categories.add(category);
        category.addStudy(this);

    }

    @Builder
    public Study (User owner, String title, String explanation, int timePerWeek, double hourPerTime,int tardyPenaltyAmount, int absentPenaltyAmount,
                  int assignmentPenaltyAmount, int targetUserNumber, String deadline, List<Category> categories) {
        this.owner = owner;
        this.title = title;
        this.explanation = explanation;
        validTimePerWeek(timePerWeek);
        this.timePerWeek = timePerWeek;
        validHourPerTime(hourPerTime);
        this.hourPerTime = hourPerTime;
        checkPenaltyValidation(tardyPenaltyAmount);
        this.tardyPenaltyAmount = tardyPenaltyAmount;
        checkPenaltyValidation(absentPenaltyAmount);
        this.absentPenaltyAmount = absentPenaltyAmount;
        checkPenaltyValidation(assignmentPenaltyAmount);
        this.assignmentPenaltyAmount = assignmentPenaltyAmount;
        this.targetUserNumber = targetUserNumber;
        this.createTime = LocalDate.now();
        this.deadline = LocalDate.parse(deadline, DateTimeFormatter.ISO_DATE);
        for (Category category : categories) {
            this.addCategory(category);
        }
    }

    public void update(StudyUpdateDto studyUpdateDto) {
        this.title = studyUpdateDto.getTitle();
        this.explanation = studyUpdateDto.getExplanation();
        // 현재 수락인원보다 적으면 exception
        this.targetUserNumber = studyUpdateDto.getTargetUserNumber();
        this.imageUrl = studyUpdateDto.getImageUrl();
        // 현 시점보다 과거일 경우 exception
        this.deadline = studyUpdateDto.getDeadline();
    }

    private void checkPenaltyValidation(int amount) {
        if (amount<0 || amount > 10000) {
            throw new IllegalStateException();
        }
    }

    public void checkMemberNumber() {
        if (this.getParticipatingUsers().size()>this.targetUserNumber) {
            throw new IllegalStateException("모집 인원 초과입니다.");
        } else if (this.getParticipatingUsers().size()==this.targetUserNumber) {
            this.isRecruitClosed = true;
        }
    }

    public void validTimePerWeek(int time) {
        if (time < 1 || time > 7) {
            throw new IllegalStateException("유효하지 않은 횟수입니다.");
        }
    }

    public void validHourPerTime(double hour) {
        if (hour < 0.5 || hour > 4) {
            throw new IllegalStateException("유효하지 않은 시간입니다.");
        }
    }

    // 화상 회의를 열거나 닫는다
    public void openAndCloseConference(Boolean isOpen) {
        if (this.isConferenceOpened == isOpen) {
            if (isOpen)
                throw new IllegalStateException("이미 화상회의가 열려있습니다.");
            else
                throw new IllegalStateException("이미 화상회의가 닫혀있습니다.");
        }
        this.isConferenceOpened = isOpen;
    }
}