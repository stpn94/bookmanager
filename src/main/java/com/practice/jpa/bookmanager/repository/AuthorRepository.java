package com.practice.jpa.bookmanager.repository;

import com.practice.jpa.bookmanager.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}