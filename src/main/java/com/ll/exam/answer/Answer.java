package com.ll.exam.answer;

import com.ll.exam.question.Question;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer {

    @Id //Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment
    private Integer id;

    @Column(columnDefinition = "TEXT") //TEXT
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    //answer에 question foriengkey를 안쓰게 truncate는 fk없어야 가능, 다른 방법은 Repositroy에 native query로 fk 설정 끄는방법
    //@JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Question question;
}
