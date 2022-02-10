package com.practice.jpa.bookmanager.repository;

import com.practice.jpa.bookmanager.domain.Book;
import com.practice.jpa.bookmanager.domain.BookReviewInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

@SpringBootTest
class BookReviewInfoRepositoryTest {
    @Autowired
    private BookReviewInfoRepository bookReviewInfoRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    void crudTest() {
        givenBookReviewInfo(givenBook());
        // 북리뷰정보 출력

    }

    @Test
    void crudTest2() {
        givenBookReviewInfo(givenBook());
        System.out.println(">>> " + bookReviewInfoRepository.findAll());

        // 북리뷰정보에 있는 Id가 1인 컬럼의 Id로 book 검색.
        Book result = bookReviewInfoRepository
                .findById(1L)
                .orElseThrow(RuntimeException::new)
                .getBook();
        System.out.println("result  >>>> " + result);

        BookReviewInfo result2 = bookRepository
                .findById(1L)
                .orElseThrow(RuntimeException::new)
                .getBookReviewInfo();
        System.out.println("result2 >>>> " + result2);
    }

    private void givenBookReviewInfo(Book book) {
        // 북리뷰정보 출력
        BookReviewInfo bookReviewInfo = new BookReviewInfo();
//        bookReviewInfo.setBookId(1L);
        bookReviewInfo.setBook(book);
        bookReviewInfo.setAverageReviewScore(4.5f);
        bookReviewInfo.setReviewCount(2);
        bookReviewInfoRepository.save(bookReviewInfo);


    }

    private Book givenBook() {
        // 북 하나추가.
        Book book = new Book();
        book.setName("Jpa 초격차 패키지");
        book.setAuthorId(1L);
//        book.setPublisherId(1L);
        // 북 목록
//        System.out.println(">>>> " + bookRepository.findAll());
        return bookRepository.save(book);
    }
}