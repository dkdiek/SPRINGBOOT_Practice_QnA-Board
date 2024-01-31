package com.ll.exam.answer;

import com.ll.exam.DataNotFoundException;
import com.ll.exam.question.Question;
import com.ll.exam.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public void create(Question question, String content, SiteUser author) {

            Answer answer = new Answer();
            answer.setContent(content);
            answer.setCreateDate(LocalDateTime.now());
            answer.setAuthor(author);
            question.addAnswer(answer);

            answerRepository.save(answer);
    }

    public Answer getAnswer(Integer id) {
        return answerRepository.findById(id).orElseThrow(()-> new DataNotFoundException("answer not found"));
    }

}
