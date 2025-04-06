package com.example.demo.service.Impl;

import com.example.demo.dto.EntityWhereDto;
import com.example.demo.service.EntitySqlServive;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class EntitySqlServiceImpl implements EntitySqlServive {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 根據 JPA 的 @Column(name) 自動生成 INSERT SQL 並執行
     * @param entityInstance    資料表的 實體
     */
    @Transactional
    public void insertEntity(Object entityInstance) {
        // 取得 tableName
        String entityName = getTableName(entityInstance.getClass());

        // 取得 entity 欄位與數值
        Map<String, Object> entityMap = getNameAndValues(entityInstance);

        // 動態生成 SQL
        // (1) 組合 前置SQL
        StringBuilder sql = new StringBuilder("INSERT INTO ")
                .append(entityName).append(" (");
        StringBuilder sqlValue = new StringBuilder("VALUES (");
        // (2) 遍歷 entity 欄位與數值 設定SQL
        for (Map.Entry<String, Object> entity : entityMap.entrySet()) {
            if (entity.getValue() != null) {
                String columnName = entity.getKey();
                sql.append(columnName).append(",");
                sqlValue.append(":").append(columnName).append(",");
            }
        }
        // (3) 動態SQL 刪除結尾 ","
        sql.setLength(sql.length()-1);
        sqlValue.setLength(sqlValue.length()-1);
        // (4) 組合結尾
        sql.append(") ").append(sqlValue).append(")");

        // 執行 SQL
        Query execute = entityManager.createNativeQuery(sql.toString());
        for (Map.Entry<String, Object> entity : entityMap.entrySet()) {
            if (entity.getValue() != null) {
                execute.setParameter(entity.getKey(), entity.getValue());
            }
        }
        execute.executeUpdate();
    }

    /**
     * 根據 JPA 的 @Column(name) 自動生成 UPDATE SQL 並執行
     * @param oldEntityInstance 資料表的 舊實體
     * @param newEntityInstance 資料表的 新實體
     */
    @Transactional
    public void updateEntity(Object oldEntityInstance, Object newEntityInstance) {
        if (!oldEntityInstance.getClass().equals(newEntityInstance.getClass())) {
            throw new RuntimeException("舊實體和新實體必須是相同類型");
        }

        // 取得 tableName
        String entityName = getTableName(oldEntityInstance.getClass());

        // 取得 entity 欄位與數值
        Map<String, Object> oldEntityMap = getNameAndValues(oldEntityInstance);
        Map<String, Object> newEntityMap = getNameAndValues(newEntityInstance);

        // 動態生成 SQL
        // (1) 組合 SET
        StringBuilder sql = new StringBuilder("UPDATE ")
                .append(entityName).append(" SET ");
        for (Map.Entry<String, Object> entity : newEntityMap.entrySet()) {
            sql.append(entity.getKey()).append("=:new_").append(entity.getKey()).append(",");
        }
        sql.setLength(sql.length()-1);

        // (2) 組合 WHERE
        StringBuilder sqlWhere = new StringBuilder("WHERE ");
        for (Map.Entry<String, Object> entity : oldEntityMap.entrySet()) {
            sqlWhere.append(entity.getKey()).append("=:old_").append(entity.getKey()).append(" AND ");
        }
        sqlWhere.setLength(sqlWhere.length()-4);

        // (3) 組合結尾
        sql.append(" ").append(sqlWhere);

        // 執行 SQL
        Query execute = entityManager.createNativeQuery(sql.toString());
        for (Map.Entry<String, Object> entity : newEntityMap.entrySet()) {
            String entityKey = "new_" + entity.getKey();
            execute.setParameter(entityKey, entity.getValue());
        }
        for (Map.Entry<String, Object> entity : oldEntityMap.entrySet()) {
            String entityKey = "old_" + entity.getKey();
            execute.setParameter(entityKey, entity.getValue());
        }
        sqlWhere.setLength(sqlWhere.length()-4);
        execute.executeUpdate();
    }

    /**
     * 根據 JPA 的 @Column(name) 自動生成 DELETE SQL 並執行
     * @param entityInstance 資料表的 實體
     */
    @Transactional
    public void deleteEntity(Object entityInstance) {
        // 取得 tableName
        String entityName = getTableName(entityInstance.getClass());

        // 取得 entity 欄位與數值
        Map<String, Object> entityMap = getNameAndValues(entityInstance);

        // 動態生成 SQL
        // (1) 組合 SQL
        StringBuilder sql = new StringBuilder("DELETE FROM ")
                .append(entityName).append(" WHERE ");
        for (Map.Entry<String, Object> entity : entityMap.entrySet()) {
            sql.append(entity.getKey()).append("=:").append(entity.getKey()).append(" AND ");
        }
        // (2) 動態SQL 刪除結尾 ","
        sql.setLength(sql.length()-4);;

        // 執行 SQL
        Query execute = entityManager.createNativeQuery(sql.toString());
        for (Map.Entry<String, Object> entity : entityMap.entrySet()) {
            if (entity.getValue() != null) {
                execute.setParameter(entity.getKey(), entity.getValue());
            }
        }
        execute.executeUpdate();
    }

    /**
     * 根據 JPA 的 @Table(name) 自動生成 SELECT SQL 並執行
     * 查詢條件 限定為 AND
     * @param tableClass 要查詢的 table Class
     * @param whereList 查詢條件
     * @return
     * @param <T>
     */
    public <T> List<T> selectEntity(Class<T> tableClass, List<EntityWhereDto> whereList) {
        return selectEntity(tableClass, whereList, null);
    }

    /**
     * 根據 JPA 的 @Table(name) 自動生成 SELECT SQL 並執行
     * 查詢條件 限定為 AND
     * @param tableClass 要查詢的 table Class
     * @param whereList 查詢條件
     * @param otherSql 其他SQL語句
     * @return
     * @param <T>
     */
    public <T> List<T> selectEntity(Class<T> tableClass, List<EntityWhereDto> whereList, String otherSql) {
        // 取得 tableName
        String entityName = getTableName(tableClass);

        // 動態生成 SQL
        // (1) 設定 查詢所有欄位
        StringBuilder sql = new StringBuilder("SELECT ");
        for (Field field : tableClass.getDeclaredFields()) {
            String columnName = getColumnName(field);
            String attributeName = field.getName();
            sql.append(columnName).append(" AS ").append(attributeName).append(",");
        }
        sql.setLength(sql.length()-1);
        sql.append(" FROM ").append(entityName).append(" WHERE 1=1 ");
        // (2) 設定 查詢條件
        for (EntityWhereDto where : whereList) {
            sql.append("AND ");
            sql.append(where.getColumnName()).append(" ")
                    .append(where.getOperator()).append(" :").append(where.getColumnName());
        }
        // (3) 設定 其他SQL語句
        if (!StringUtils.isEmpty(otherSql)) {
            sql.append(" ").append(otherSql);
        }
        System.err.println(sql.toString());

        // 執行 SQL
        NativeQuery query = entityManager.createNativeQuery(sql.toString())
                .unwrap(NativeQuery.class);
        // (1) 設定 參數
        for (EntityWhereDto where : whereList) {
            query.setParameter(where.getColumnName(), where.getValue());
        }
        // (2) 設定 欄位類型
        for (Field field : tableClass.getDeclaredFields()) {
            query.addScalar(field.getName(), field.getType());
        }
        // (3) 設定 自動映射到 tableClass，並輸出為 List
        return query.setResultTransformer(Transformers.aliasToBean(tableClass)).getResultList();
    }


    /**
     * 根據 JPA 的 @Column(name) 獲取 資料表實例 的 欄位及數值
     * @param entity    資料表的 實例
     * @return  Map: key=entityName, value=entityValue
     */
    private Map<String, Object> getNameAndValues(Object entity) {
        Map<String, Object> entityData = new HashMap<>();

        // 取得 entity 實體 所有欄位的 Field
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 獲取 columnName
            String columnName = getColumnName(field);
            // 獲取 columnValue
            field.setAccessible(true);
            try {
                Object columnValue = field.get(entity);
                entityData.put(columnName, columnValue);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to access field value");
            }
        }
        return entityData;
    }
    /**
     * 根據 entity 的 @Table(name) 獲取 tableName
     * @param entityClass   資料表的 類對象
     * @return tableName
     */
    private String getTableName(Class<?> entityClass) {
        // 取得 @Table 的資料
        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        // 有 @Table(name) 才取得 tableName，否則 取得 className
        if (tableAnnotation != null && !tableAnnotation.name().isEmpty()) {
            return tableAnnotation.name();
        } else {
            return entityClass.getName();
        }
    }

    /**
     * 根據 entity 的 @Column(name) 獲取 columnName
     * @param field entity 欄位 透過反射獲取的 Field 物件
     * @return columnName
     */
    private String getColumnName(Field field) {
        // 取得 @Column 的資料
        Column columnAnnotation = field.getAnnotation(Column.class);
        // 有 @Column(name) 才取得 columnName，否則 取得 欄位名稱
        if (columnAnnotation != null) {
            return columnAnnotation.name();
        } else {
            return field.getName();
        }
    }
}



