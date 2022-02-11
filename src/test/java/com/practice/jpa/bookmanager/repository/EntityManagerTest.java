package com.practice.jpa.bookmanager.repository;

import com.practice.jpa.bookmanager.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class EntityManagerTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    @Test
    void entityManagerTest() {
        System.out.println(entityManager.createQuery("select u from User u").getResultList());
        // userRepository.findAll(); 이거와 같다.
    }

    @Test
    void cacheFindTest() {
//        System.out.println(userRepository.findByEmail("martin@fast.com"));
//        System.out.println(userRepository.findByEmail("martin@fast.com"));
//        System.out.println(userRepository.findByEmail("martin@fast.com"));
//        System.out.println(userRepository.findById(2L).get());
//        System.out.println(userRepository.findById(2L).get());
//        System.out.println(userRepository.findById(2L).get());

        userRepository.deleteById(1L);
    }

    @Test
    void cacheFindTest2() {
        User user = userRepository.findById(1L).get();
        user.setName("marrrrrrrtin");
        userRepository.save(user);

        System.out.println("---------------------");

        user.setEmail("marrrrrrtin@fast.com");
        userRepository.save(user);

        userRepository.flush();

    }
}