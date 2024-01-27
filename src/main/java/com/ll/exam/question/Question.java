package com.ll.exam.question;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity //아래 Question 클래스는 엔티티 클래스이다 //아래 클래스와 1:1로 매칭되는 테이블이 db 없다면 , 자동으로 생성되어야 한다.
public class Question {

    @Id //Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment
    private Integer id;
    @Column(length = 200) //Varchar(200)
    private String subject;
    @Column(columnDefinition = "TEXT") //TEXT
    private String content;
    private LocalDateTime createDate;
}
