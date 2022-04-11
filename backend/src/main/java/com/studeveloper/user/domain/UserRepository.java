package com.studeveloper.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserAuthId(String userAuthId);
    boolean existsByUserAuthId(String userAuthId);
}
