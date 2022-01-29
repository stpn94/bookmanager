package com.practice.jpa.bookmanager.repository;

import com.practice.jpa.bookmanager.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 스프링 데이터 JPA 지지원영역
    // 자바 인터페이스를 선언해주고 JpaRepository<Entity Type, PK>를 상속받는거 만으로 많은 JPA 관련기능을 지원해준다.

}
