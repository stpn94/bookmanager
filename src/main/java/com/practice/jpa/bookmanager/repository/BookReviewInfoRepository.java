package com.practice.jpa.bookmanager.repository;

import com.practice.jpa.bookmanager.domain.BookReviewInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewInfoRepository extends JpaRepository<BookReviewInfo, Long> {
}