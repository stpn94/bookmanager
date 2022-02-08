package com.practice.jpa.bookmanager.domain.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class MyEntityListener {
    @PrePersist
    public void prePersist(Object o) { //해당 오브젝트를 파라미터로 받아야하기 때문에 오브젝트를 강제한다
        if (o instanceof Auditable) {  //받아오 오브젝트가 Auditable 객체인지 확인하고
            ((Auditable) o).setCreatedAt(LocalDateTime.now());
            ((Auditable) o).setUpdatedAt(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate(Object o){
        if (o instanceof Auditable) {  //받아오 오브젝트가 Auditable 객체인지 확인하고
            ((Auditable) o).setUpdatedAt(LocalDateTime.now());
        }

    }
}
