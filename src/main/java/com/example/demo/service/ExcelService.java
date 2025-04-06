package com.example.demo.service;

import java.util.List;

public interface ExcelService {
    /**
     * 產出 客戶地址明細表
     * @param clientId
     * @return
     */
    byte[] exportDemo02(String clientId);


    /**
     * 產出 客戶地址明細表 (排序)
     * @param clientIdList
     * @return
     */
    byte[] exportDemo08(List<String> clientIdList);
}
