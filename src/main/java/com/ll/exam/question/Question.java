package com.ll.exam.question;

import com.ll.exam.answer.Answer;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data //Getter, Setter 붙인거랑 같다 toString까지 만들어짐
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

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) //Answer 테이블의 question 컬럼과 매핑, cascadetype 엔티티를 제거할 때, 연관된 하위 엔티티도 모두 제거한다. 질문이 삭제되면 해당 질문의 answer들도 삭제된다
    private List<Answer> answerList;
}
