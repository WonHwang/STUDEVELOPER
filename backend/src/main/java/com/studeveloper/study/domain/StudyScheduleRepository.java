package com.studeveloper.study.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyScheduleRepository extends JpaRepository<StudySchedule, Long> {
    Optional<List<StudySchedule>> findAllByStudy(Study study);
}
