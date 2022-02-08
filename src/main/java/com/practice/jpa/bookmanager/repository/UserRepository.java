package com.practice.jpa.bookmanager.repository;

import com.practice.jpa.bookmanager.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface UserRepository extends JpaRepository<User, Long> {
    // 스프링 데이터 JPA 지지원영역
    // 자바 인터페이스를 선언해주고 JpaRepository<Entity Type, PK>를 상속받는거 만으로 많은 JPA 관련기능을 지원해준다.

    List<User> findByName(String name); //이름을 통해 유저 가져오는 메서드.

    User findByEmail(String email);
    User getByEmail(String email);
    User readByEmail(String email);
    User queryByEmail(String email);
    User searchByEmail(String email);
    User streamByEmail(String email);

    User findUserByEmail(String email);       // 비추. User는 이미 명시되어있다.
    User findSomethingByEmail(String email);  // 이렇게 해도 되지만, 비추. 명시적으로 하자

    List<User> findFirst2ByName(String name);
    List<User> findTop2ByName(String name);
    List<User> findLast1ByName(String name);

    List<User> findByEmailAndName(String email, String name);
    List<User> findByEmailOrName(String email, String name);
    List<User> findByCreatedAtAfter(LocalDateTime yesterday);
    List<User> findByIdAfter(Long id);
    List<User> findByCreatedAtGreaterThan(LocalDateTime yesterday);
    List<User> findByCreatedAtGreaterThanEqual(LocalDateTime yesterday);
    List<User> findByCreatedAtBetween(LocalDateTime yesterday, LocalDateTime tomorrow);
    List<User> findByIdBetween(Long id1, Long id2);
    List<User> findByIdGreaterThanEqualAndIdLessThanEqual(Long id1, Long id2);
    List<User> findByIdIsNotNull();
//    List<User> findByAddressIsNotEmpty();   // name is not null and name != '' ??
    List<User> findByNameIn(List<String> names);
    List<User> findByNameStartingWith(String name);
    List<User> findByNameEndingWith(String name);
    List<User> findByNameContains(String name);
    List<User> findByNameLike(String name);

    // findLastByName을 만들기 (역순으로 정렬후 가장 위에 값) (Asc는 정순, Desc는 역순)
    List<User> findTopByNameOrderByIdDesc(String name);
    // findLastByName (Id역순으로 이메일 정순으호하여 첫번째 데이터)
    List<User> findFirstByNameOrderByIdDescEmailAsc(String name);

    // 파라미터 기반
    // 이름 조건으로 찾은 첫번째 데이터를 'sort'로 정렬 ex : (name: "martin",Sort.by(Order.desc("id))
    // 코드 가독성을 위해 sort인자를 파라미터로 주면 좋다.
    // 하지만 코드가 많이 중복될수 있다. 적절하게 사용하는게 중요하다.
    // 인터페이스에서는 함수하나 선언해주고 사용하는 곳에서 원하는 sort를 쓰면 된다.
    List<User> findFirstByName(String name, Sort sort);

    /* 보통 많은 수의 데이터를 List 형식으로 받아온다. 그래서 패이징 처리가 필수 인데, Jpa에서는 아주 쉽게 페이징을 지원해준다.
    * 중요한건 2가지
    * 1. Page<User> 타입 = Page인터페이스 확인해보면 Slice(데이터의 일부 묶음)을 상속받아서 제공한다. ex) getContent()
    * 1-1. Page 인터페이스에 Slice 에서 제공받은 함수 이외에도 getTotalElement() 같은 전체 레코드에 관한 기능도 제공한다.
    * 2. Pageable 인터페이스 = 파라미터로 사용하는 pageable은 Pageable에 대한 요청값 이다.
    * 2-1. pageable 파라미터가 요청 값 이면 Page는 응답 타입 이다.
    *  */
    Page<User> findByName(String name, Pageable pageable);

    @Query(value = "select * from user limit 1;", nativeQuery = true)
    Map<String, Object> findRawRecord();


}
