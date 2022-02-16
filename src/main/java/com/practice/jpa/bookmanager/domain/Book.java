package com.practice.jpa.bookmanager.domain;

import com.practice.jpa.bookmanager.converter.BookStatusConverter;
import com.practice.jpa.bookmanager.domain.listener.Auditable;
import com.practice.jpa.bookmanager.repository.dto.BookStatus;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)                //상속받은 클래스에 대해 처리해줘야한다. ToString을 재정의 한다.
@EqualsAndHashCode(callSuper = true)       //EqualsAndHashCode를 재정의해준다.
//@EntityListeners(value = AuditingEntityListener.class)
@DynamicUpdate
@Where(clause = "deleted =false")
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private Long authorId;
//    private Long publisherId;

    @OneToMany  // 1 : N \ Book : Review
    @JoinColumn(name = "book_id")   //중간 테이블 방지
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE} )  // N : 1 \ Book : publisher
    @ToString.Exclude
    private Publisher publisher;


    @OneToOne(mappedBy = "book")
    @ToString.Exclude // ToString 순환참조 걸린다. 릴레이션은 단방향으로 걸고 ToString은 제외해야한다.
    private BookReviewInfo bookReviewInfo;

    //    @ManyToMany
    @OneToMany
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    private List<BookAndAuthor> bookAndAuthors = new ArrayList<>();

    public void addBookAndAuthors(BookAndAuthor... bookAndAuthors) {
        Collections.addAll(this.bookAndAuthors, bookAndAuthors);
    }

    private boolean deleted; // 기본=false

    @Convert(converter = BookStatusConverter.class) //어떤 컨버터 class 쓸지 지정
    private BookStatus status; // 판매상태

//    @CreatedDate
//    private LocalDateTime createdAt;
//
//    @LastModifiedDate
//    private LocalDateTime updatedAt;

//    @PrePersist
//    public void prePersist() {
//        this.createdAt = LocalDateTime.now();
//        this.updatedAt = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    public void preUpdate() {
//        this.updatedAt = LocalDateTime.now();
//    }
}
