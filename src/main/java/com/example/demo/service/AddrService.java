package com.example.demo.service;

import com.example.demo.entity.Addr;

import java.util.List;

public interface AddrService {

    /**
     * 新增/修改 資料 (單筆)
     * @param addr
     * @return
     */
    Addr save(Addr addr);

    /**
     * 新增/修改 資料 (多筆)
     * @param addrList
     * @return
     */
    List<Addr> saveAll(List<Addr> addrList);

    /**
     * 根據 主鍵(clientId) 查詢資料
     * @param addrKey clientId + addrInd
     * @return
     */
    Addr findById(Addr.AddrKey addrKey);

    /**
     * 根據 主鍵集合(clientId) 查詢資料
     * @param addrKeyList clientId + addrInd 集合
     * @return
     */
    List<Addr> findAllById(List<Addr.AddrKey> addrKeyList);

    /**
     * 根據 主鍵(clientId) 刪除 資料
     * @param addrKey clientId + addrInd
     */
    void deleteById(Addr.AddrKey addrKey);

    /**
     * 根據 主鍵集合(clientId) 刪除 資料
     * @param addrKeyList clientId + addrInd 集合
     */
    void deleteAllById(List<Addr.AddrKey> addrKeyList);

    /**
     * 根據 客戶證號 查詢資料
     * @param clientId
     * @return
     */
    List<Addr> findByClientId(String clientId);

    /**
     * 取得 E指示
     * @param clientId
     * @return
     */
    Addr findEAddrByClientId(String clientId);
}
