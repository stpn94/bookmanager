package com.practice.jpa.bookmanager.repository;

import com.practice.jpa.bookmanager.domain.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}