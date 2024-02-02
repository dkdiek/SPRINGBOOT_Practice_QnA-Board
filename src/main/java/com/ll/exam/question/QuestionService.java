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

    public Page<Question> getList(String kw, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        sorts.add(Sort.Order.desc("id")); //작성일시가 같으면 다음 id 기준으로 정렬

        Pageable pageable = PageRequest.of(page, 10,  Sort.by(sorts));

        if(kw == null || kw.trim().length() == 0){
            return questionRepository.findAll(pageable);
        }

        return questionRepository.findDistinctBySubjectContainsOrContentContainsOrAuthor_usernameContainsOrAnswerList_contentContainsOrAnswerList_Author_usernameContains(kw, kw, kw, kw, kw, pageable);
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

    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());

        this.questionRepository.save(question);
    }

    public void delete(Question question) {
        this.questionRepository.delete(question);
    }

    public void vote(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }
}
