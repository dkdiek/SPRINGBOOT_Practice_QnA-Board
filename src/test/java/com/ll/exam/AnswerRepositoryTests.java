package com.ll.exam;

import com.ll.exam.answer.Answer;
import com.ll.exam.answer.AnswerRepository;
import com.ll.exam.question.Question;
import com.ll.exam.question.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AnswerRepositoryTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @BeforeEach
    @Transactional
    @Rollback(value = false)
    void beforeEach(){
        clearData(); // 데이터 삭제
        createSampleData(); // 데이터 만들기
    }

    public static void clearData(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        QuestionRepositoryTests.clearData(questionRepository);
        answerRepository.deleteAll();
        answerRepository.truncateTable();
    }

    private void clearData(){
        clearData(answerRepository, questionRepository);
    }

    private void createSampleData() {
        QuestionRepositoryTests.createSampleData(questionRepository);

        Question q = questionRepository.findById(Math.toIntExact(1)).get();

        Answer a1 = new Answer();
        a1.setContent("SBB는 질문답변 게시판입니다");
        a1.setCreateDate(LocalDateTime.now());
        q.addAnswer(a1);

        Answer a2 = new Answer();
        a2.setContent("SBB에서는 주로 스프링관련 내용을 다룹니다");
        a2.setCreateDate(LocalDateTime.now());
        q.addAnswer(a2);

        questionRepository.save(q);
    }


    @Test
    @Transactional
    @Rollback(value = false)
    void 저장() {
        Question q = questionRepository.findById(Math.toIntExact(2)).get();

        Answer a1 = new Answer();
        a1.setContent("네 자동으로 생성됩니다.");
        a1.setCreateDate(LocalDateTime.now());
        q.addAnswer(a1);

        Answer a2 = new Answer();
        a2.setContent("네네 맞습니다!!");
        a2.setCreateDate(LocalDateTime.now());
        q.addAnswer(a2);

        questionRepository.save(q);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void 조회() {
        Answer a = this.answerRepository.findById(Math.toIntExact(1)).get();
        assertThat(a.getContent()).isEqualTo("SBB는 질문답변 게시판입니다");
    }
    @Test
    @Transactional
    @Rollback(value = false)
    void 관련된_question_조회() {
        Answer a = this.answerRepository.findById(Math.toIntExact(1)).get();
        Question q = a.getQuestion();

        assertThat(q.getId()).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void question으로부터_관련된_질문들_조회() {
        //SELECT * FROM question WHERE id=1;
        Question q = questionRepository.findById(Math.toIntExact(1)).get();
        //DB 연결이 끊김

        //SELECT * FROM answer WHERE question_id=1;
        List<Answer> answerList = q.getAnswerList();

        assertThat(answerList.size()).isEqualTo(2);
        assertThat(answerList.get(0).getContent()).isEqualTo("SBB는 질문답변 게시판입니다");
    }




}
