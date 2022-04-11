package com.studeveloper.study.service;

import com.studeveloper.category.domain.Category;
import com.studeveloper.category.service.CategoryService;
import com.studeveloper.study.domain.*;
import com.studeveloper.study.dto.StudyRequestDto;
import com.studeveloper.study.dto.StudySearchDto;
import com.studeveloper.study.dto.schedule.StudyScheduleRequestDto;
import com.studeveloper.study.dto.schedule.StudyScheduleResponseDto;
import com.studeveloper.study.dto.StudyUpdateDto;
import com.studeveloper.study.dto.task.TaskRequestDto;
import com.studeveloper.study.dto.task.TaskResponseDto;
import com.studeveloper.study.utils.FileStore;
import com.studeveloper.study.utils.StudyScheduleConverter;
import com.studeveloper.study.utils.TaskConverter;
import com.studeveloper.user.domain.User;
import com.studeveloper.user.dto.UserApplyResponseDto;
import com.studeveloper.user.service.UserService;
import com.studeveloper.user.utils.UserConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import java.util.ArrayList;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyServiceImpl implements StudyService{
    public static final Logger logger = LoggerFactory.getLogger(StudyServiceImpl.class);
    private final StudyRepository studyRepository;
    private final StudyScheduleRepository studyScheduleRepository;
    private final UserStudyRepository userStudyRepository;
    private final TaskRepository taskRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    private final FileStore fileStore;


    @Override
    public Study save(Authentication authentication, StudyRequestDto studyRequestDto) {
        User requestUser = userService.findRequestUser(authentication);
        List<Category> categories = studyRequestDto.getCategories().stream().map(categoryService::findByName).collect(Collectors.toList());
        Study study = Study.builder()
                .owner(requestUser)
                .title(studyRequestDto.getTitle())
                .explanation(studyRequestDto.getExplanation())
                .hourPerTime(studyRequestDto.getHourPerTime())
                .timePerWeek(studyRequestDto.getTimePerWeek())
                .tardyPenaltyAmount(studyRequestDto.getTardyPenaltyAmount())
                .absentPenaltyAmount(studyRequestDto.getAbsentPenaltyAmount())
                .assignmentPenaltyAmount(studyRequestDto.getAssignmentPenaltyAmount())
                .targetUserNumber(studyRequestDto.getTargetUserNumber())
                .deadline(studyRequestDto.getDeadline())
                .categories(categories)
                .build();
        UserStudy userStudy = new UserStudy(requestUser, study);
        userStudy.create();
        studyRepository.save(study);
        userStudyRepository.save(userStudy);
        return study;
    }

    @Override
    public List<Study> findAll() {
        return studyRepository.findAll();
    }

    @Override
    public Study findById(Long studyId) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(RuntimeException::new);
        return study;
    }

    @Override
    public Study update(Authentication authentication, Long studyId, StudyUpdateDto studyUpdateDto) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(RuntimeException::new);
        userService.checkAllowedUser(authentication, study.getOwner());
        study.update(studyUpdateDto);
        return studyRepository.save(study);

    }

    @Override
    public void delete(Authentication authentication, Long studyId) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(RuntimeException::new);
        userService.checkAllowedUser(authentication, study.getOwner());
        studyRepository.delete(study);
    }

    @Override
    public void apply(Authentication authentication, Long studyId) {
        // 유저 스터디 신청 여부, 마감 여부 파악하는 로직 추가
        User user = userService.findRequestUser(authentication);
        Study study = findById(studyId);
        if(userStudyRepository.findByUserIdAndStudyId(user.getId(), studyId) != null){
            throw new IllegalStateException("이미 스터디에 지원하였습니다.");
        }
        UserStudy userStudy = new UserStudy(user, study);
        userStudyRepository.save(userStudy);
    }

    @Override
    public void cancelApply(Authentication authentication, Long studyId) {
        User user = userService.findRequestUser(authentication);
        Study study = findById(studyId);
        UserStudy userStudy = userStudyRepository.findByUserIdAndStudyId(user.getId(), studyId);
        userStudyRepository.delete(userStudy);
    }

    @Override
    public void denyApply(Authentication authentication, Long studyId, Long userId) {
        Study study = findById(studyId);
        User owner = study.getOwner();
        userService.checkAllowedUser(authentication,owner);
        User appliedUser = userService.findById(userId);
        UserStudy userStudy = userStudyRepository.findByUserIdAndStudyId(userId, studyId);
        userStudy.deny();
        userStudyRepository.save(userStudy);
    }

    @Override
    public void acceptApply(Authentication authentication, Long studyId, Long userId) {
        Study study = findById(studyId);
        User owner = study.getOwner();
        userService.checkAllowedUser(authentication,owner);
        User appliedUser = userService.findById(userId);
        UserStudy userStudy = userStudyRepository.findByUserIdAndStudyId(userId, studyId);
        userStudy.accept();
        userStudyRepository.save(userStudy);
    }

    @Override
    public void openAndCloseConference(Authentication authentication, Long studyId, Boolean isOpen) {
        Study study = findById(studyId);
        User owner = study.getOwner();
        logger.info(owner.getId().toString());
        logger.info(owner.getName());
        logger.info(study.getTitle());
        userService.checkAllowedUser(authentication, owner);
        logger.info(isOpen.toString());
        study.openAndCloseConference(isOpen);
        studyRepository.save(study);
    }

    // 유저가 참여중인 스터디 목록을 가져온다
    @Override
    public List<Study> getStudyListParticipating(Authentication authentication, Long userId) {
        User user = userService.findRequestUser(authentication);
        List<UserStudy> userStudyList = userStudyRepository.findAllByUserId(userId);
        List<Study> studyList = new ArrayList<>();
        for (UserStudy userStudy : userStudyList) {
//            logger.info(userStudy.getStudy().getTitle());
            if(userStudy.isAccepted())
                studyList.add(findById(userStudy.getStudy().getId()));
        }
        return studyList;
    }

    // 자신이 스터디장인 스터디 목록을 가져온다
    @Override
    public List<Study> getStudyListHead(Authentication authentication, Long userId) {
        User user = userService.findById(userId);
        userService.checkAllowedUser(authentication, user);
        return studyRepository.findAllByOwner(user);
    }

    // 유저가 참여중이면서 화상 회의가 열린 스터디 목록을 가져온다
    @Override
    public List<Study> getStudyListOpen(Authentication authentication, Long userId) {
        User user = userService.findRequestUser(authentication);
        List<UserStudy> userStudyList = userStudyRepository.findAllByUserId(userId);
        List<Study> studyList = new ArrayList<>();
        for (UserStudy userStudy : userStudyList) {
            Study study = findById(userStudy.getStudy().getId());
            logger.info(String.valueOf(userStudy.isAccepted()));
            if (study.isConferenceOpened() && userStudy.isAccepted())
                studyList.add(study);
        }
        return studyList;
    }

    public void registerSchedule(Authentication authentication,Long studyId, StudyScheduleRequestDto studyScheduleInfo) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(IllegalStateException::new);
        userService.checkAllowedUser(authentication, study.getOwner());
        LocalDate studyDate = LocalDate.parse(studyScheduleInfo.getStudyDate(), DateTimeFormatter.ISO_DATE);
        StudySchedule studySchedule = new StudySchedule(studyScheduleInfo.getTitle(), studyScheduleInfo.getExplanation(), studyDate, study);
        studyScheduleRepository.save(studySchedule);
    }

    @Override
    public List<StudyScheduleResponseDto> getSchedule(Long studyId) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(IllegalStateException::new);
        List<StudySchedule> studySchedules = studyScheduleRepository.findAllByStudy(study)
                .orElseThrow(IllegalStateException::new);
        return StudyScheduleConverter.toDtos(studySchedules);
    }

    @Override
    public void registerTask(Authentication authentication, Long studyScheduleId, TaskRequestDto taskInfo) throws IOException {
        User user = userService.findRequestUser(authentication);
        Task task = fileStore.storeFile(taskInfo.getTaskFile());
        StudySchedule schedule = studyScheduleRepository.findById(studyScheduleId).orElseThrow(RuntimeException::new);
        task.setStudySchedule(schedule);
        task.setRegisterTime();
        task.setPostUser(user);
        taskRepository.save(task);
    }

    @Override
    public List<TaskResponseDto> getTasks(Long studyScheduleId) {
        StudySchedule studySchedule = studyScheduleRepository.findById(studyScheduleId)
                .orElseThrow(IllegalStateException::new);
        List<Task> tasks = studySchedule.getTasks();
        return TaskConverter.toDtos(tasks);
    }

    @Override
    public String getUploadFileName(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(IllegalStateException::new);
        return UriUtils.encode(task.getUploadFileName(), StandardCharsets.UTF_8);
    }

    @Override
    public UrlResource getUrlResource(Long taskId) throws MalformedURLException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(IllegalStateException::new);
        return new UrlResource("file:" + fileStore.getFullPath(task.getStoreFileName()));
    }

    @Override
    public List<UserApplyResponseDto> getApplyUsers(Long studyId) {
        List<UserStudy> userStudyList = userStudyRepository.findAllByStudyId(studyId);
        List<UserApplyResponseDto> userApplyResponseDtoList = new ArrayList<>();
        for(UserStudy userStudy: userStudyList){
            User user = userService.findById(userStudy.getUser().getId());
            userApplyResponseDtoList.add(UserConverter.toApplyDto(user, userStudy));
        }
        return userApplyResponseDtoList;
    }

    @Override
    public List<Study> searchStudy(String title, String sort) {
        List<Study> studyList = new ArrayList<>();
        if (title.equals("null"))
            studyList = studyRepository.findAll();
        else
            studyList = studyRepository.findByTitleContainingIgnoreCase(title);
        logger.info(String.valueOf(studyList.size()));
        if (sort.equals("최신순")) {
            studyList.sort((o1, o2) -> {
                return o2.getCreateTime().toString().compareTo(o1.getCreateTime().toString());
            });
        } else if (sort.equals("마감임박순")) {
            studyList.sort((o1, o2) -> {
                return Double.compare(o2.getParticipatingUsers().size() / (double) o2.getTargetUserNumber(), o1.getParticipatingUsers().size() / (double) o1.getTargetUserNumber());
            });
        } else if (sort.equals("인원적은순")) {
            studyList.sort((o1, o2) -> {
                return o1.getParticipatingUsers().size() - o2.getParticipatingUsers().size();
            });
        } else if (sort.equals("인원많은순")) {
            studyList.sort((o1, o2) -> {
                return o2.getParticipatingUsers().size() - o1.getParticipatingUsers().size();
            });
        } else {
            throw new IllegalStateException("유효하지 않은 정렬방법입니다.");
        }
        return studyList;
    }
}
