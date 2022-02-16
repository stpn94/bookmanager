package com.practice.jpa.bookmanager.service;

import com.practice.jpa.bookmanager.domain.Book;
import com.practice.jpa.bookmanager.repository.AuthorRepository;
import com.practice.jpa.bookmanager.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final AuthorService authorService;

    //    public void put(){
//        this.putBookAndAuthor();
//    }
//    @Transactional(rollbackFor = Exception.class)
    @Transactional(propagation = Propagation.REQUIRED)
    public void putBookAndAuthor() {
        Book book = new Book();
        book.setName("JPA 시작하기");

        bookRepository.save(book);
        try {
            authorService.putAuthor();
        } catch (RuntimeException e) {
        }
//        Author author = new Author();
//        author.setName("martin");
//
//        authorRepository.save(author);
//
        throw new RuntimeException("오류가 발생 트랜젝션 어떻게 됨?"); // unchecked Exception
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void get(Long id) {
        System.out.println(">>findById>> " + bookRepository.findById(id));
        System.out.println(">>findAll>> " + bookRepository.findAll());

        System.out.println(">>findById>> " + bookRepository.findById(id));
        System.out.println(">>findAll>> " + bookRepository.findAll());
        bookRepository.update();
//        Book book = bookRepository.findById(id).get();
//        book.setName("바뀔까?");
//        bookRepository.save(book);
    }

    @Transactional
    public List<Book> getAll() {
        List<Book> books = bookRepository.findAll();

        books.forEach(System.out::println);

        return books;
    }
}
