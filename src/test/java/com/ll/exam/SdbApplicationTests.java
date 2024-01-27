package com.ll.exam;

import com.ll.exam.question.Question;
import com.ll.exam.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class SdbApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;

    //insert
    @Test
    void testJpa() {
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);  // 첫번째 질문 저장

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2);  // 두번째 질문 저장

        assertThat(q1.getId()).isGreaterThan(0); //id가 최소 0보다 크다
        assertThat(q2.getId()).isGreaterThan(q1.getId()); // 1번째 아이디보다 크다
    }

    //select all
    @Test
    void testJpa2() {
        List<Question> all = this.questionRepository.findAll();
        assertEquals(2, all.size());

        Question q = all.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }


    @Test
    void testJpa3() {
        Question q = questionRepository.findByContent("dfd?");
        System.out.println(q);
    }

    @Test
    void testJpa4() {
        Question q = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?","sbb에 대해서 알고 싶습니다.");
        assertEquals(1,q.getId());
    }
    @Test
    void testJpa5() {
        List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
        Question q = qList.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }

    @Test
    void testJpa6() {
        Optional<Question> oq = this.questionRepository.findById(1);
        assertThat(oq.isPresent());
        Question q = oq.get();
        q.setSubject("수정된 제목");

        questionRepository.save(q);
    }

}
