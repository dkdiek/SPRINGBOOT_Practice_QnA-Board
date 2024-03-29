package com.ll.exam.question;

import com.ll.exam.answer.Answer;
import com.ll.exam.user.SiteUser;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data //Getter, Setter 붙인거랑 같다 toString까지 만들어짐
@Entity //아래 Question 클래스는 엔티티 클래스이다 //아래 클래스와 1:1로 매칭되는 테이블이 db 없다면 , 자동으로 생성되어야 한다.
public class Question {

    @Id //Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment
    private int id;

    @Column(length = 200) //Varchar(200)
    private String subject;

    @Column(columnDefinition = "TEXT") //TEXT
    private String content;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    @OneToMany(mappedBy = "question", cascade = {CascadeType.ALL}) //Answer 테이블의 question 컬럼과 매핑, cascadetype 엔티티를 제거할 때, 연관된 하위 엔티티도 모두 제거한다. 질문이 삭제되면 해당 질문의 answer들도 삭제된다
    private List<Answer> answerList = new ArrayList<>();

    @ManyToOne //질문 여러개 to 유저 1명
    private SiteUser author;



    public void addAnswer(Answer answer) {
        answer.setQuestion(this);
        getAnswerList().add(answer);
    }

    @ManyToMany
    Set<SiteUser> voter;
}
