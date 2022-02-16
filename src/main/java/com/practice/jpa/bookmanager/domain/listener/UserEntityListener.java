package com.practice.jpa.bookmanager.domain.listener;

import com.practice.jpa.bookmanager.domain.User;
import com.practice.jpa.bookmanager.domain.UserHistory;
import com.practice.jpa.bookmanager.repository.UserHistoryRepository;
import com.practice.jpa.bookmanager.support.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class UserEntityListener {
    /*
     * User의 정보가 생성,수정 될때, 그 User객체를 받아서 History정보를 저장한다.
     * */

//    @Autowired
//    private UserHistoryRepository userHistoryRepository;

    @PostPersist
    @PostUpdate
    public void prePersistAndPreUpdate(Object o) {
        /*
        * 여기서 주의해야할 점은 Listener는 @Autowired로 빈을 가져오지 못한다. 그래서 BeanUtils클래스를 이용해서 주입해 줘야 한다.
        * */
        UserHistoryRepository userHistoryRepository = BeanUtils.getBean(UserHistoryRepository.class);
        System.out.println("User 생성 및 수정 하기전에 History남기는 쿼리");
        User user = (User) o;

        UserHistory userHistory = new UserHistory();
        userHistory.setName(user.getName());
        userHistory.setEmail(user.getEmail());
        userHistory.setUser(user);  //user객체를 넣는다.
        userHistory.setHomeAddress(user.getHomeAddress());
        userHistory.setCompanyAddress(user.getCompanyAddress());
        userHistoryRepository.save(userHistory);
    }
}
