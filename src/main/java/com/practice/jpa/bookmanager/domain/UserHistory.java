package com.practice.jpa.bookmanager.domain;

import com.practice.jpa.bookmanager.domain.listener.Auditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
//@EntityListeners(value = AuditingEntityListener.class)
public class UserHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // user의 insert update에는 영향을 주지 않아야함. // ManyToOne할때는 불필요한 컬럼이다.
//    @Column(name = "user_id", insertable = false,updatable = false)
//    private Long userId;

    private String name;
    private String email;

    @ManyToOne  // 다 대 일
    private User user;
//    @CreatedDate
//    private LocalDateTime createdAt;
//    @LastModifiedDate
//    private LocalDateTime updatedAt;
}