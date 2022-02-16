package com.practice.jpa.bookmanager.converter;

import com.practice.jpa.bookmanager.repository.dto.BookStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter //JPA에서 사용하는 Converter이다 라는 표시
public class BookStatusConverter implements AttributeConverter<BookStatus,Integer> {

    @Override
    public Integer convertToDatabaseColumn(BookStatus attribute) {
        // 여기는 BookStatus의 객체를 받아서 DataBase에 저장할때 어떻게 할꺼냐?
        // Code를 가져와서 넣어줄꺼다.
        return attribute.getCode();
    }

    @Override
    public BookStatus convertToEntityAttribute(Integer dbData) {
        // DB에서 Integer값을 받았을때는 어떻게 변환할꺼냐??
        // BookStatus를 만들어 줄 꺼다.
        // 그리고 nullPointException이 일어나면 절때 안된다. DB에 접근하는 Data이기 때문에 민감하다.
        return dbData != null ? new BookStatus(dbData) : null;
    }
}
