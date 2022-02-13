package com.practice.jpa.bookmanager.service;

import com.practice.jpa.bookmanager.domain.Book;
import com.practice.jpa.bookmanager.repository.AuthorRepository;
import com.practice.jpa.bookmanager.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookServiceTest {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Test
    void transactionTest() {
        // 이런 코드는 지양하지만 학습을위한 Test기 때문에 이렇게 한다.
        try{
            bookService.putBookAndAuthor();
        }catch (RuntimeException e){
            System.out.println(">>>> " + e.getMessage());
        }
        System.out.println("books : " + bookRepository.findAll());
        System.out.println("authors : " + authorRepository.findAll());
    }

    @Test
    void isolationTest(){
        Book book = new Book();
        book.setName("JPA 강의");

        bookRepository.save(book);
        bookService.get(1L);

        System.out.println(">>>> " +bookRepository.findAll());
    }
}