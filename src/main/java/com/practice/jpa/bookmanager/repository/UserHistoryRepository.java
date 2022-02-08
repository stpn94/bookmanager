package com.practice.jpa.bookmanager.repository;

import com.practice.jpa.bookmanager.domain.User;
import com.practice.jpa.bookmanager.domain.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
    List<UserHistory> findByUserId(Long userId);
}
