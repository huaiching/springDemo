package com.example.demo.controller;

import com.example.demo.entity.Clnt;
import com.example.demo.repository.ClntRepositroy;
import com.example.demo.service.ClntService;
import com.example.demo.service.ExcelService;
import com.example.demo.service.WordService;
import com.example.demo.util.FileUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Tag(name = "報表產出", description = "報表產出 API 接口")
@RestController
@RequestMapping("/export")
public class ExportController {
    @Autowired
    WordService wordService;
    @Autowired
    ExcelService excelService;


    @Operation(summary = "產出 客戶證號明細表 (word)", description = "產出 客戶證號明細表 (word)")
    @GetMapping("/exportDemo01")
    public ResponseEntity<Resource> exportDemo01(@RequestParam String clientId) throws UnsupportedEncodingException {
        byte[] fileBytes = wordService.exportDemo01(clientId);
        String fileName = "客戶證號明細表.docx";

        return FileUtil.responseEntity(fileName, fileBytes);
    }

    @Operation(summary = "產出 客戶地址明細表 (excel)", description = "產出 客戶地址明細表 (excel)")
    @GetMapping("/exportDemo02")
    public ResponseEntity<Resource> exportDemo02(@RequestParam String clientId) throws UnsupportedEncodingException {
        byte[] fileBytes = excelService.exportDemo02(clientId);
        String fileName = "客戶地址明細表.xlsx";

        return FileUtil.responseEntity(fileName, fileBytes);
    }


    @Operation(summary = "產出 客戶地址明細表 (排序) (excel)", description = "產出 客戶地址明細表 (排序) (excel)")
    @PostMapping("/exportDemo08")
    public ResponseEntity<Resource> exportDemo08(@RequestBody List<String> clientIdList) throws UnsupportedEncodingException {
        byte[] fileBytes = excelService.exportDemo08(clientIdList);
        String fileName = "客戶地址明細表(排序).xlsx";

        return FileUtil.responseEntity(fileName, fileBytes);
    }

}
