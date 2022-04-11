package com.studeveloper.study.service;

import com.studeveloper.study.domain.Study;
import com.studeveloper.study.domain.UserStudy;
import com.studeveloper.study.dto.StudyRequestDto;
import com.studeveloper.study.dto.StudySearchDto;
import com.studeveloper.study.dto.schedule.StudyScheduleRequestDto;
import com.studeveloper.study.dto.schedule.StudyScheduleResponseDto;
import com.studeveloper.study.dto.StudyUpdateDto;
import com.studeveloper.study.dto.task.TaskRequestDto;
import com.studeveloper.study.dto.task.TaskResponseDto;
import com.studeveloper.user.domain.User;
import com.studeveloper.user.dto.UserApplyResponseDto;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;


public interface StudyService {
    Study save(Authentication authentication, StudyRequestDto studyRequestDto);
    List<Study> findAll();
    Study findById(Long studyId);
    Study update(Authentication authentication, Long studyId, StudyUpdateDto studyUpdateDto);
    void delete(Authentication authentication, Long studyId);
    void apply(Authentication authentication, Long studyId);
    void cancelApply(Authentication authentication, Long studyId);
    void denyApply(Authentication authentication, Long studyId, Long userId);
    void acceptApply(Authentication authentication, Long studyId, Long userId);
    void openAndCloseConference(Authentication authentication, Long studyId, Boolean isOpen);
    List<Study> getStudyListParticipating(Authentication authentication, Long userId);
    List<Study> getStudyListHead(Authentication authentication, Long userId);
    List<Study> getStudyListOpen(Authentication authentication, Long userId);
    void registerSchedule(Authentication authentication,Long studyId, StudyScheduleRequestDto studyScheduleInfo);
    List<StudyScheduleResponseDto> getSchedule(Long studyId);
    void registerTask(Authentication authentication, Long studyScheduleId, TaskRequestDto taskInfo) throws IOException;
    List<TaskResponseDto> getTasks(Long studyScheduleId);
    String getUploadFileName(Long taskId);
    UrlResource getUrlResource(Long taskId) throws MalformedURLException;
    List<UserApplyResponseDto> getApplyUsers(Long studyId);
    List<Study> searchStudy(String title, String sort);
}
