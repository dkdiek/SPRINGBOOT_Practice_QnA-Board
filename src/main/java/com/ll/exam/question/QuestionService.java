package com.ll.exam.question;

import com.ll.exam.DataNotFoundException;
import com.ll.exam.user.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        //sorts.add(Sort.Order.desc("id")); //작성일시가 같으면 다음 id 기준으로 정렬

        Pageable pageable = PageRequest.of(page, 10,  Sort.by(sorts));
        return questionRepository.findAll(pageable);
    }

    public Question getQuestion(int id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("no %d question not found".formatted(id)));
    }

    public void create(String subject, String content, SiteUser author) {
            Question q = new Question();
            q.setSubject(subject);
            q.setContent(content);
            q.setCreateDate(LocalDateTime.now());
            q.setAuthor(author);
            this.questionRepository.save(q);
    }
}
