package com.studeveloper.study.domain;

import com.studeveloper.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {
    List<Study> findAllByOwner(User user);
    List<Study> findByTitleContainingIgnoreCase(String title);
}
