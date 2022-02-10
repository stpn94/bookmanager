package com.practice.jpa.bookmanager.repository;

import com.practice.jpa.bookmanager.domain.Book;
import com.practice.jpa.bookmanager.domain.Publisher;
import com.practice.jpa.bookmanager.domain.Review;
import com.practice.jpa.bookmanager.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void bookTest() {
        Book book = new Book();
        book.setName("Jpa 초격차 패키지");
        book.setAuthorId(1L);
//        book.setPublisherId(1L);
        bookRepository.save(book);
        System.out.println(bookRepository.findAll());
    }

    @Test
    @Transactional //트랜젝션할때 배운다.
    void bookRelationTest() {
        // 북 정보 저장.
        givenBookAndReview();

        //유저 값 불러오자. 원래는 인증 Data에서 받아오겠지??
        User user = userRepository.findByEmail("martin@fast.com");

        System.out.println("Review : " + user.getReviews());    // 유저에 리뷰 보기
        System.out.println("Book : " + user.getReviews().get(0).getBook()); // 유저의 리뷰의 책정보 불러오기
        System.out.println("Publisher : " + user.getReviews().get(0).getBook().getPublisher()); // 유저의 리뷰의 책정보에 출판사 정보
    }

    private void givenBookAndReview() {
        givenReview(givenUser(), givenBook(givenPublisher()));
    }

    private User givenUser() {
        //유저 값 불러오자.
        return userRepository.findByEmail("martin@fast.com");
    }

    private void givenReview(User user, Book book) {
        // 리뷰
        Review review = new Review();
        review.setTitle("내 인생을 바꾼 책");
        review.setContent("너무너무 재미있고 즐거운 책이었어요.");
        review.setScore(5.0f);
        review.setUser(user);   // user와 관계
        review.setBook(book);   // book과 관계

        reviewRepository.save(review);
    }

    private Book givenBook(Publisher publisher) {
        // 책 하나 만들어 주자.
        Book book = new Book();
        book.setName("JPA 초격차 패키지");
        book.setPublisher(publisher); // 출판사는 publisher를 생성해서 받아와야겠지?

        return bookRepository.save(book);
    }

    private Publisher givenPublisher() {
        // 출판사 하나 생성해주자.
        Publisher publisher = new Publisher();
        publisher.setName("패스트캠퍼스");

        return publisherRepository.save(publisher);
    }
}