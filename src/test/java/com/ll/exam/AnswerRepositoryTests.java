package com.ll.exam;

import com.ll.exam.answer.Answer;
import com.ll.exam.answer.AnswerRepository;
import com.ll.exam.question.Question;
import com.ll.exam.question.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class AnswerRepositoryTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;
    private static int lastSampleDataId;

    @BeforeEach
    void beforeEach(){
        clearData(); // 데이터 삭제
        createSampleData(); // 데이터 만들기
    }

    private void clearData() {
        QuestionRepositoryTests.clearData(questionRepository);
        answerRepository.truncateTable();
    }

    private void createSampleData() {
        QuestionRepositoryTests.createSampleData(questionRepository);
    }


    @Test
    void 저장() {
        Question q = questionRepository.findById(2).get();
        Answer a = new Answer();
        a.setContent("네 자동으로 생성됩니다.");
        a.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
        a.setCreateDate(LocalDateTime.now());
        answerRepository.save(a);
    }






}
