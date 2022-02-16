package com.practice.jpa.bookmanager.repository;

import com.practice.jpa.bookmanager.domain.Book;
import com.practice.jpa.bookmanager.domain.Publisher;
import com.practice.jpa.bookmanager.domain.Review;
import com.practice.jpa.bookmanager.domain.User;
import com.practice.jpa.bookmanager.repository.dto.BookStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

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
    void converterTest(){
        bookRepository.findAll().forEach(System.out::println);
        // 새로운 책 생성하나 해보자.
        Book book = new Book();
        book.setName("또다른 IT전문서적");
        book.setStatus(new BookStatus(200));

        bookRepository.save(book);
        // 실제 DB에 있는 값이 200으로 잘 변환되어있는지 보자.
        System.out.println(bookRepository.findRawRecord().values());
    }

    @Test
    void nativeQueryTest() {
//        bookRepository.findAll().forEach(System.out::println);
//        System.out.println("==========================================");
//        bookRepository.findAllCustom().forEach(System.out::println);
        System.out.println("==========================================");

        List<Book> books = bookRepository.findAll();

        for (Book book : books) {
            book.setCategory("IT전문서");
        }

        bookRepository.saveAll(books);

        System.out.println(bookRepository.findAll());

        System.out.println("affected rows : " + bookRepository.updateCategories());

        bookRepository.findAllCustom().forEach(System.out::println);

        System.out.println(bookRepository.showTables());
    }


    @Test
    void queryTest() {
        bookRepository.findAll().forEach(System.out::println);

        System.out.println("findByCategoryIsNullAndNameEqualsAndCreatedAtGreaterThanEqualAndUpdatedAtGreaterThanEqual : " +
                bookRepository.findByCategoryIsNullAndNameEqualsAndCreatedAtGreaterThanEqualAndUpdatedAtGreaterThanEqual(
                        "JPA 작은격차 클래스",
                        LocalDateTime.now().minusDays(1L),
                        LocalDateTime.now().minusDays(1L)
                ));

        System.out.println("findByNameRecently : " +
                bookRepository.findByNameRecently(
                        "JPA 작은격차 클래스",
                        LocalDateTime.now().minusDays(1L),
                        LocalDateTime.now().minusDays(1L)));

        System.out.println(bookRepository.findBookNameAndCategory());
        bookRepository.findBookNameAndCategory().forEach(b -> {
            System.out.println(b.getName() + " : " + b.getCategory());
        });

        bookRepository.findBookNameAndCategory(PageRequest.of(1, 1)).forEach(
                bookNameAndCategory -> System.out.println(bookNameAndCategory.getName() + " : " + bookNameAndCategory.getCategory()));

        bookRepository.findBookNameAndCategory(PageRequest.of(0, 1)).forEach(
                bookNameAndCategory -> System.out.println(bookNameAndCategory.getName() + " : " + bookNameAndCategory.getCategory()));
    }

    @Test
    void softDelete() {
        bookRepository.findAll().forEach(System.out::println);
        System.out.println(bookRepository.findById(3L));

        bookRepository.findByCategoryIsNull().forEach(System.out::println);

        bookRepository.findAllByDeletedFalse().forEach(System.out::println);
        bookRepository.findByCategoryIsNullAndDeletedFalse().forEach(System.out::println);

    }

    @Test
    void bookRemoveCascadeTest() {
        bookRepository.deleteById(1L);

        System.out.println("books : " + bookRepository.findAll());
        System.out.println("publishers " + publisherRepository.findAll());

        bookRepository.findAll().forEach(book -> System.out.println(book.getPublisher()));
    }

    @Test
    void bookCascadeTest() {
        // 영속성 전이 테스트
        // 책 생성 후 저장
        Book book = new Book();
        book.setName("JPA 초격차 패키지");
//        bookRepository.save(book);

        // 출판사 생성
        Publisher publisher = new Publisher();
        publisher.setName("빠른대학");
//        publisherRepository.save(publisher);

        // 책의 출판사를 set
        book.setPublisher(publisher);
        bookRepository.save(book);

        // 출판사의 책을 set
        // callByValue callByRef
        // publisher는 List이기 때문에 하나의 책(객체)를 불러와서 book으로 넣는다.
        // publisher.getBooks().add(book);

//        publisher.addBook(book); // 위에 코드 보다 조금더 가독성있다.
        // 하지만 setter를 사용하여 직관적으로 표현하는게 더 좋다.
        // 이런경우에는 Publisher클래스에 addBook()함수를 하나 만드는 것도 좋은 방법이다.

//        publisherRepository.save(publisher);
        System.out.println("book : " + bookRepository.findAll());
        System.out.println("publishers : " + publisherRepository.findAll());

        Book book1 = bookRepository.findById(1L).get(); // 학습용 코드 실무에서는 이렇게 안함.
        book1.getPublisher().setName("느린대학");
        bookRepository.save(book1);
        System.out.println("publishers : " + publisherRepository.findAll());

        Book book2 = bookRepository.findById(1L).get();
        bookRepository.delete(book2);
//        bookRepository.deleteById(1L);
//        publisherRepository.delete((book2.getPublisher()));

        Book book3 = bookRepository.findById(1L).get();
        book3.setPublisher(null);

        bookRepository.save(book3);

        System.out.println("books : " + bookRepository.findAll());
        System.out.println("publishers : " + publisherRepository.findAll());
        System.out.println("book3 : " + bookRepository.findById(1L).get().getPublisher());
    }

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
    @Transactional
        //트랜젝션할때 배운다.
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