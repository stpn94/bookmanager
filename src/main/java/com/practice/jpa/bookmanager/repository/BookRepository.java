package com.practice.jpa.bookmanager.repository;

import com.practice.jpa.bookmanager.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {


}
