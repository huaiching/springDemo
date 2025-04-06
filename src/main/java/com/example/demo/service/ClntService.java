package com.example.demo.service;

import com.example.demo.entity.Clnt;

import java.util.List;
import java.util.Optional;

public interface ClntService {

    /**
     * 新增/修改 資料 (單筆)
     * @param clnt
     * @return
     */
    public Clnt save(Clnt clnt);

    /**
     * 新增/修改 資料 (多筆)
     * @param clntList
     * @return
     */
    public List<Clnt> saveAll(List<Clnt> clntList);

    /**
     * 根據 主鍵(clientId) 查詢資料
     *
     * @param clientId
     * @return
     */
    public Optional<Clnt> findById(String clientId);

    /**
     * 根據 主鍵集合(clientId) 查詢資料
     * @param clientIdList
     * @return
     */
    public List<Clnt> findAllById(List<String> clientIdList);

    /**
     * 根據 主鍵(clientId) 刪除 資料
     * @param clientId
     */
    public void deleteById(String clientId);

    /**
     * 根據 主鍵集合(clientId) 刪除 資料
      * @param clientIdList
     */
    public void deleteAllById(List<String> clientIdList);

    /**
     * 動態SQL新增資料 (單筆)
     * @param clnt
     * @return
     */
    public void insert(Clnt clnt);

}
