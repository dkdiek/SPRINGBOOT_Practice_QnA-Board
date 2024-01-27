package com.ll.exam.question;

import com.ll.exam.answer.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
