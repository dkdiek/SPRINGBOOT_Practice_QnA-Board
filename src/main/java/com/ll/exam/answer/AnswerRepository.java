package com.ll.exam.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    @Modifying
    @Transactional
    @Query(value ="TRUNCATE answer", nativeQuery = true)
    void truncate();
}
