package com.studeveloper.study.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserStudyRepository extends JpaRepository<UserStudy, Long> {
//    Optional<UserStudy> findByUserAndStudy();
    List<UserStudy> findAllByUserId(Long userId);
    List<UserStudy> findAllByStudyId(Long studyId);
    UserStudy findByUserIdAndStudyId(Long userId, Long studyId);
}
