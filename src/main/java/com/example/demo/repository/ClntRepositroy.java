package com.example.demo.repository;

import com.example.demo.entity.Clnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClntRepositroy extends JpaRepository<Clnt, String> {

    /**
     * 根據 姓名 查詢資料
     * @param names
     * @return
     */
    @Query(value = "SELECT * FROM clnt " +
            "WHERE names LIKE :names", nativeQuery = true)
    List<Clnt> findByNames(@Param("names") String names);
}

