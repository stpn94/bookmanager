package com.practice.jpa.bookmanager.domain;

import com.practice.jpa.bookmanager.domain.listener.Auditable;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@ToString(callSuper = true)                //상속받은 클래스에 대해 처리해줘야한다. ToString을 재정의 한다.
@EqualsAndHashCode(callSuper = true)       //EqualsAndHashCode를 재정의해준다.
//@EntityListeners(value = AuditingEntityListener.class)
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private Long authorId;
    private Long publisherId;

    @OneToOne(mappedBy = "book")
    @ToString.Exclude // ToString 순환참조 걸린다. 릴레이션은 단방향으로 걸고 ToString은 제외해야한다.
    private BookReviewInfo bookReviewInfo;


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
