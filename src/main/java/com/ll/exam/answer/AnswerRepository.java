package com.ll.exam.answer;

import com.ll.exam.base.RepositoryUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AnswerRepository extends JpaRepository<Answer, Integer>, RepositoryUtil {

    @Modifying
    @Transactional
    @Query(value ="TRUNCATE answer", nativeQuery = true)
    void truncate(); // 지우면 안된다 truncateTable하면 자동으로 실행된다

}
