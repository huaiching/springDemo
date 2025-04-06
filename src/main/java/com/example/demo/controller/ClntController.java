package com.example.demo.controller;

import com.example.demo.dto.EntityWhereDto;
import com.example.demo.entity.Benf;
import com.example.demo.entity.Clnt;
import com.example.demo.repository.ClntRepositroy;
import com.example.demo.service.ClntService;
import com.example.demo.service.EntitySqlServive;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "客戶資料檔", description = "客戶資料檔 API 接口")
@RestController
@RequestMapping("/clnt")
public class ClntController {
    @Autowired
    private ClntService clntService;
    @Autowired
    private ClntRepositroy clntRepositroy;
    @Autowired
    private EntitySqlServive entitySqlServive;

    @Operation(summary = "保存資料 (單筆)", description = "無ID=新增 / 有ID=更新")
    @PostMapping("/save")
    public ResponseEntity<Clnt> save(@RequestBody Clnt clnt) {
        return ResponseEntity.ok(clntService.save(clnt));
    }

    @Operation(summary = "保存資料資料 (多筆)", description = "無ID=新增 / 有ID=更新")
    @PostMapping("/saveAll")
    public ResponseEntity<List<Clnt>> saveAll(@RequestBody List<Clnt> clntList) {
        return ResponseEntity.ok(clntService.saveAll(clntList));
    }

    @Operation(summary = "查詢資料: 根據 主鍵(clientId) ", description = "查詢單筆資料")
    @GetMapping("/findById")
    public ResponseEntity<Optional<Clnt>> findById(@RequestParam String clientId) {
        return ResponseEntity.ok(clntService.findById(clientId));
    }

    @Operation(summary = "查詢資料: 根據 主鍵集合(clientId) ", description = "查詢多筆資料")
    @PostMapping("/findAllById")
    public ResponseEntity<List<Clnt>> findAllById(@RequestBody List<String> clientIdList) {
        return ResponseEntity.ok(clntService.findAllById(clientIdList));
    }

    @Operation(summary = "刪除資料: 根據 主鍵(clientId) ", description = "刪除單筆資料")
    @DeleteMapping("/deleteById")
    public ResponseEntity<Void> deleteById(@RequestParam String clientId) {
        clntService.deleteById(clientId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "刪除資料: 根據 主鍵集合(clientId) ", description = "刪除多筆資料")
    @DeleteMapping("/deleteAllById")
    public ResponseEntity<Void> deleteAllById(@RequestBody List<String> clientIdList) {
        clntService.deleteAllById(clientIdList);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "查詢資料: 根據 姓名 ", description = "查詢多筆資料")
    @GetMapping("/findByNames")
    public ResponseEntity<List<Clnt>> findByNames(@RequestParam String names) {
        return ResponseEntity.ok(clntRepositroy.findByNames(names));
    }


    @Operation(summary = "動態SQL新增資料 (單筆)", description = "動態SQL新增資料 (單筆)")
    @PostMapping("/insert")
    public ResponseEntity<Clnt> insert(@RequestBody Clnt clnt) {
        clntService.insert(clnt);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "(動態SQL) 查詢資料", description = "(動態SQL) 查詢資料")
    @PostMapping("/select")
    public ResponseEntity<List<Clnt>> select(@RequestBody List<EntityWhereDto> whereList) {
        List<Clnt> benfList = entitySqlServive.selectEntity(Clnt.class, whereList);

        return ResponseEntity.ok(benfList);
    }
}
