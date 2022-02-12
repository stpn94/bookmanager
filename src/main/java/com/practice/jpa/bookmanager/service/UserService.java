package com.practice.jpa.bookmanager.service;

import com.practice.jpa.bookmanager.domain.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {
    @Transactional
    public void put(){
        User user = new User();
        user.setName("newUser");
        user.setEmail("newUser@fast.com");
    }
}
