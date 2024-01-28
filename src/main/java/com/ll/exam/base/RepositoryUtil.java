package com.ll.exam.base;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RepositoryUtil {

    @Modifying
    @Transactional
    @Query(value ="SET FOREIGN_KEY_CHECKS =0;", nativeQuery = true) //FK 사용 DISALBE
    void disableForeignKeyChecks();

    @Modifying
    @Transactional
    @Query(value ="SET FOREIGN_KEY_CHECKS =1;", nativeQuery = true) //FK 사용 ENABLE
    void enableForeignKeyChecks();

}
