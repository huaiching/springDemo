package com.example.demo.controller;

import com.example.demo.dto.BenfUpdateDto;
import com.example.demo.dto.ClientIdDto;
import com.example.demo.dto.EntityWhereDto;
import com.example.demo.entity.Benf;
import com.example.demo.entity.Clnt;
import com.example.demo.service.EntitySqlServive;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "受益人檔", description = "受益人檔 API 接口")
@RestController
@RequestMapping("/benf")
public class BenfController {
//    @Autowired
//    private EntitySqlServive entitySqlServive;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

//    @Operation(summary = "(動態SQL) 新增資料", description = "(動態SQL) 新增資料")
//    @PostMapping("/insert")
//    public ResponseEntity<Clnt> insert(@RequestBody Benf benf) {
//        entitySqlServive.insertEntity(benf);
//        return ResponseEntity.ok().build();
//    }
//
//    @Operation(summary = "(動態SQL) 修改資料", description = "(動態SQL) 修改資料")
//    @PostMapping("/update")
//    public ResponseEntity<Clnt> update(@RequestBody BenfUpdateDto benfUpdateDto) {
//        entitySqlServive.updateEntity(benfUpdateDto.getOldBenf(), benfUpdateDto.getNewBenf());
//        return ResponseEntity.ok().build();
//    }
//
//    @Operation(summary = "(動態SQL) 刪除資料", description = "(動態SQL) 刪除資料")
//    @PostMapping("/delete")
//    public ResponseEntity<Clnt> delete(@RequestBody Benf benf) {
//        entitySqlServive.deleteEntity(benf);
//        return ResponseEntity.ok().build();
//    }
//
//    @Operation(summary = "(動態SQL) 查詢資料", description = "(動態SQL) 查詢資料")
//    @PostMapping("/select")
//    public ResponseEntity<List<Benf>> select(@RequestBody List<EntityWhereDto> whereList) {
//        List<Benf> benfList = entitySqlServive.selectEntity(Benf.class, whereList);
//
//        return ResponseEntity.ok(benfList);
//    }


    @Operation(summary = "(jdbcTemplate) 查詢資料", description = "(jdbcTemplate) 查詢資料")
    @PostMapping("/select")
    public ResponseEntity<List<Benf>> testSelect(@RequestBody ClientIdDto clientId) {
        // 設定查詢SQL
        String sql = "SELECT * FROM benf WHERE client_id = :clientId";
        // 設定參數
        Map<String, Object> params = new HashMap<>();
        params.put("clientId", clientId.getClientId());

        // 查詢資料
        List<Benf> result = namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Benf.class));

        return ResponseEntity.ok(result);
    }
}

