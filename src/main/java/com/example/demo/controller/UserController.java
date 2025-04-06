package com.example.demo.controller;

import com.example.demo.dto.BenfUpdateDto;
import com.example.demo.dto.EntityWhereDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Benf;
import com.example.demo.entity.Clnt;
import com.example.demo.service.EntitySqlServive;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;

@Tag(name = "客戶資料查詢", description = "客戶資料查詢 API 接口")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private EntityManager entityManager;

    @Operation(summary = "(動態SQL) 新增資料", description = "(動態SQL) 新增資料")
    @PostMapping("/queryUserData")
    public ResponseEntity<List<UserDto>> insert(@RequestParam String clientId) {

        // 設定 SQL 語句，條件使用 參數「:」 方式定義，不要直接寫出來
        String sql = "SELECT a.client_id AS clientId," +
                "a.names AS names," +
                "a.birth_date AS birthDate," +
                "a.sex AS sex," +
                "b.tel_1 AS phone," +
                "b.address AS email " +
                "FROM clnt a " +
                "LEFT OUTER JOIN addr b " +
                "ON a.client_id = b.client_id " +
                "AND b.addr_ind = 'E' " +
                "WHERE a.client_id = :clientId ";
        // 設定 hibernate 原生SQL 實例，並 設定參數
        NativeQuery query = entityManager.createNativeQuery(sql).unwrap(NativeQuery.class)
                .setParameter("clientId", clientId);
        // 設定 查詢結果的型態
        // 透過 class 的 getDeclaredFields() 可以取得 DTO 的所有欄位
        for (Field field : UserDto.class.getDeclaredFields()) {
            query.addScalar(field.getName(), field.getType());
        }
        // 設定 查詢結果的自動映射
        query.setResultTransformer(Transformers.aliasToBean(UserDto.class));
        // 執行 SQL，並返回查詢結果
        List<UserDto> userDtoList = query.getResultList();

        return ResponseEntity.ok(userDtoList);
    }

}

