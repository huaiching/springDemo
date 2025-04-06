package com.example.demo.service;

import com.example.demo.dto.EntityWhereDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EntitySqlServive {

    /**ager entityManager;

     /**
     * 根據 JPA 的 @Column(name) 自動生成 INSERT SQL 並執行
     * @param entityInstance    資料表的 實體
     */
    void insertEntity(Object entityInstance);

    /**
     * 根據 JPA 的 @Column(name) 自動生成 UPDATE SQL 並執行
     * @param oldEntityInstance 資料表的 舊實體
     * @param newEntityInstance 資料表的 新實體
     */
    void updateEntity(Object oldEntityInstance, Object newEntityInstance);

    /**
     * 根據 JPA 的 @Column(name) 自動生成 DELETE SQL 並執行
     * @param entityInstance 資料表的 實體
     */
    @Transactional
    void deleteEntity(Object entityInstance);

    /**
     * 根據 JPA 的 @Table(name) 自動生成 SELECT SQL 並執行
     * 查詢條件 限定為 AND
     * @param tableClass 要查詢的 table Class
     * @param whereList 查詢條件
     * @return
     * @param <T>
     */
    <T> List<T> selectEntity(Class<T> tableClass, List<EntityWhereDto> whereList);

    /**
     * 根據 JPA 的 @Table(name) 自動生成 SELECT SQL 並執行
     * 查詢條件 限定為 AND
     * @param tableClass 要查詢的 table Class
     * @param whereList 查詢條件
     * @param otherSql 其他SQL語句
     * @return
     * @param <T>
     */
    <T> List<T> selectEntity(Class<T> tableClass, List<EntityWhereDto> whereList, String otherSql);
}
