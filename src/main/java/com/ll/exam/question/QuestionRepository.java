package com.ll.exam.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findBySubject(String subject);
    Question findByContent(String content);
    Question findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String s);
    @Modifying
    @Transactional
    @Query(value ="TRUNCATE question", nativeQuery = true) //mariadb 쿼리 사용
    void truncate();
}
