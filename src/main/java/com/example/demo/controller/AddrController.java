package com.example.demo.controller;

import com.example.demo.entity.Addr;
import com.example.demo.service.AddrService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "客戶地址檔", description = "客戶地址檔 API 接口")
@RestController
@RequestMapping("/addr")
public class AddrController {
    @Autowired
    private AddrService addrService;

    @Operation(summary = "保存資料 (單筆)", description = "無ID=新增 / 有ID=更新")
    @PostMapping("/save")
    public ResponseEntity<Addr> save(@RequestBody Addr addr) {
        return ResponseEntity.ok(addrService.save(addr));
    }

    @Operation(summary = "保存資料資料 (多筆)", description = "無ID=新增 / 有ID=更新")
    @PostMapping("/saveAll")
    public ResponseEntity<List<Addr>> saveAll(@RequestBody List<Addr> addrList) {
        return ResponseEntity.ok(addrService.saveAll(addrList));
    }

    @Operation(summary = "查詢資料: 根據 主鍵(clientId + addrInd) ", description = "查詢單筆資料")
    @PostMapping("/findById")
    public ResponseEntity<Addr> findById(@RequestBody Addr.AddrKey addrKey) {
        return ResponseEntity.ok(addrService.findById(addrKey));
    }

    @Operation(summary = "查詢資料: 根據 主鍵集合(clientId + addrInd) ", description = "查詢多筆資料")
    @PostMapping("/findAllById")
    public ResponseEntity<List<Addr>> findAllById(@RequestBody List<Addr.AddrKey> addrKeyList) {
        return ResponseEntity.ok(addrService.findAllById(addrKeyList));
    }

    @Operation(summary = "刪除資料: 根據 主鍵(clientId + addrInd) ", description = "刪除單筆資料")
    @DeleteMapping("/deleteById")
    public ResponseEntity<Void> deleteById(@RequestBody Addr.AddrKey addrKey) {
        addrService.deleteById(addrKey);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "刪除資料: 根據 主鍵集合(clientId + addrInd) ", description = "刪除多筆資料")
    @DeleteMapping("/deleteAllById")
    public ResponseEntity<Void> deleteAllById(@RequestBody List<Addr.AddrKey> addrKeyList) {
        addrService.deleteAllById(addrKeyList);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "查詢資料: 根據 客戶證號 ", description = "查詢單筆資料")
    @GetMapping("/findByClientId")
    public ResponseEntity<List<Addr>> findByClientId(@RequestParam("clientId") String clientId){
        return ResponseEntity.ok(addrService.findByClientId(clientId));
    }

    @Operation(summary = "查詢資料: 取得 E指示 ", description = "取得 E指示")
    @GetMapping("/findEAddrByClientId")
    public ResponseEntity<Addr> findEAddrByClientId(@RequestParam("clientId") String clientId) {
        return ResponseEntity.ok(addrService.findEAddrByClientId(clientId));
    }
}
