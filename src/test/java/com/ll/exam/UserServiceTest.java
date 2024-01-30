package com.ll.exam;

import com.ll.exam.answer.AnswerRepository;
import com.ll.exam.question.QuestionRepository;
import com.ll.exam.user.UserRepository;
import com.ll.exam.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class UserServiceTests {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
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

    private void createSampleData(UserService userService) {
        userService.create("admin","admin@test.com","1234");
        userService.create("user1","user1@test.com","1234");
    }

    private void createSampleData(){
        createSampleData(userService);
    }


    public static void clearData(UserRepository userRepository
                        , AnswerRepository answerRepository
                        , QuestionRepository questionRepository){

        answerRepository.deleteAll();
        answerRepository.truncateTable();

        questionRepository.deleteAll();
        questionRepository.truncateTable();

        userRepository.deleteAll();
        userRepository.truncateTable();
    }

    private void clearData(){
        clearData(userRepository, answerRepository, questionRepository);
    }

    @Test
    @DisplayName("회원가입이 가능하다")
    public void t1() {
        userService.create("user2","user1@email.com", "1234");
    }
}