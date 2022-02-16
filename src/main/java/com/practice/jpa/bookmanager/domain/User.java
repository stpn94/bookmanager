package com.practice.jpa.bookmanager.domain;


import com.practice.jpa.bookmanager.domain.listener.Auditable;
import com.practice.jpa.bookmanager.domain.listener.UserEntityListener;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity // 해당 객체가 JPA에서 관리하고 있는 객체인것을 정의.
@EntityListeners(value = {UserEntityListener.class})
//@Table(name = "user", indexes = {@Index(columnList = "name")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User extends BaseEntity {

    @Id // 엔티티에는 식별자가 필요한데 @ID로 표현.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // GenerationType (IDENTITY, SEQUENCE, TABLE, AUTO)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "home_city")),
            @AttributeOverride(name = "district", column = @Column(name = "home_district")),
            @AttributeOverride(name = "detail", column = @Column(name = "home_address_detail")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "home_zip_code"))
    })
    private Address homeAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "company_city")),
            @AttributeOverride(name = "district", column = @Column(name = "company_district")),
            @AttributeOverride(name = "detail", column = @Column(name = "company_address_detail")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "company_zip_code"))
    })
    private Address companyAddress;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @ToString.Exclude //순환참조 예방.
    private List<UserHistory> userHistories
            = new ArrayList<>(); //getUserHistoryes를 했을때 NullPointException이 뜨지 않게 기본리스트 넣어주자.
    // Jpa에서 해당값이 존재하지 않으면 빈 리스트를 자동으로 넣어주기는 하지만, persist하기 전에 해당 값이 Null이기 때문에 로직에 따라 오류가 생길수있다.

    @OneToMany
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();

        /*IDENTITY
    * DB에서 AUTOINCREMENT를 이용해 자동으로 ID값이 늘어나는 것 처럼 id값을 먼저 insert 시킨다.
    * 그래서 트랜젝션이 끝나기 전에 , 즉 롤백 된다하더라도 이미 insert 되어 있어서 마치 이빨빠진것 처럼 중간에 비는 현상이 일어난다.

    /*SEQUENCE
    * Sequence는 오라클, postqre에서 쓰는데 인서트를 할 때 시쿼스로부터 증가된 id 값을 받아서 실제 id값에 넣는 방식이다.
    */

    /*TABLE
     * DB종류에 상관없이 Table에 id값을 저장해 두고 추출하여 이용한다.
     */

    /*AUTO
     * 지정하지 않으면 AUTO를 쓴다. 그러면 각 DB 적합한 값을 자동으로 넘겨주는데, DB 의존성이 없이 코딩할수 있다.
     * 하지만 일반적으로 DB는 고정해서 사용하는 경우가 많아서 구체적인 값을 지정해 사용하는경우가 많다.
     */

    //    @Column(name ="crtd_at",nullable = false,unique = true)
//    @Column(insertable = false)
//    private LocalDateTime createdAt;
//    @Column(updatable = false)
//    private LocalDateTime updatedAt;

//    @Transient
//    private String testData;

/*    @PrePersist     // Insert호출 전 실행
    @PreUpdate      // marge 호출 전 실행
    @PreRemove      // Delete호출 전 실행
    @PostPersist    // Insert호출 후 실행
    @PostUpdate     // marge 호출 후 실행
    @PostRemove     // Delete호출 후 실행
    @PostLoad       // select호출 후 실행*/

//    @PrePersist
//    public void prePersist(){
//        System.out.println(">>> prePersist");
//        this.createdAt = LocalDateTime.now();
//        this.updatedAt = LocalDateTime.now();
//    }
//    @PreUpdate
//    public void preUpdate(){
//        System.out.println(">>> preUpdate");
//        this.updatedAt = LocalDateTime.now();
//    }

}