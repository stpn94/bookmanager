package com.practice.jpa.bookmanager.service;

import com.practice.jpa.bookmanager.domain.User;
import com.practice.jpa.bookmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final EntityManager entityManager;

    private final UserRepository userRepository;


    @Transactional
    public void put() {
        User user = new User();
        user.setName("newUser");
        user.setEmail("newUser@fast.com");

//        userRepository.save(user);
        entityManager.persist(user);
//        entityManager.detach(user); // 준 영속화


        user.setName("newUserAfterPersist");    //persist 한 다음에 실행한 쿼리도 flush된다.
//        entityManager.merge(user); // 재 영속화

//        entityManager.flush(); // clear하기 전에 flush()
//        entityManager.clear(); // 변경 작업에 대한 것을 모두 clear한다.

        User user1 = userRepository.findById(1L).get();
        entityManager.remove(user1);

    }

}
