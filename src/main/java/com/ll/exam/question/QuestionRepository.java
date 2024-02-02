package com.ll.exam.question;

import com.ll.exam.base.RepositoryUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer>, RepositoryUtil {
    Question findBySubject(String subject);
    Question findByContent(String content);
    Question findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String subject);

    Page<Question> findBySubjectContains(String kw, Pageable pageable);
    Page<Question> findBySubjectContainsOrContentContains(String kw, String kw_, Pageable pageable);
    Page<Question> findBySubjectContainsOrContentContainsOrAuthor_usernameContains(String kw, String kw_, String kw__, Pageable pageable);
    
    //Distinct를 붙이면 중복 제거, 원래는 답변이랑 질문 중복으로 검색 결과 나왔음
    Page<Question> findDistinctBySubjectContainsOrContentContainsOrAuthor_usernameContainsOrAnswerList_contentContainsOrAnswerList_Author_usernameContains(String kw, String kw_, String kw__, String kw___, String kw____, Pageable pageable);

    //mariadb 쿼리 사용
    @Modifying
    @Transactional
    @Query(value ="ALTER TABLE question AUTO_INCREMENT = 1", nativeQuery = true)
    void truncate();


}
