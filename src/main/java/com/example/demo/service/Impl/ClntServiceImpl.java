package com.example.demo.service.Impl;

import com.example.demo.entity.Clnt;
import com.example.demo.repository.ClntRepositroy;
import com.example.demo.service.ClntService;
import com.example.demo.service.EntitySqlServive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClntServiceImpl implements ClntService {
    @Autowired
    ClntRepositroy clntRepositroy;
    @Autowired
    EntitySqlServive entityCRUEServive;

    /**
     * 新增/修改 資料 (單筆)
     * @param clnt
     * @return
     */
    @Transactional
    public Clnt save(Clnt clnt) {
        return clntRepositroy.save(clnt);
    }

    /**
     * 新增/修改 資料 (多筆)
     * @param clntList
     * @return
     */
    @Transactional
    public List<Clnt> saveAll(List<Clnt> clntList) {
        return clntRepositroy.saveAll(clntList);
    }

    /**
     * 根據 主鍵(clientId) 查詢資料
     *
     * @param clientId
     * @return
     */
    @Override
    public Optional<Clnt> findById(String clientId) {
        return clntRepositroy.findById(clientId);
    }

    /**
     * 根據 主鍵集合(clientId) 查詢資料
     * @param clientIdList
     * @return
     */
    @Override
    public List<Clnt> findAllById(List<String> clientIdList) {
        return clntRepositroy.findAllById(clientIdList);
    }

    /**
     * 根據 主鍵(clientId) 刪除 資料
     * @param clientId
     */
    @Transactional
    public void deleteById(String clientId) {
        clntRepositroy.deleteById(clientId);
    }

    /**
     * 根據 主鍵集合(clientId) 刪除 資料
     * @param clientIdList
     */
    @Transactional
    public void deleteAllById(List<String> clientIdList) {
        clntRepositroy.deleteAllById(clientIdList);
    }

    /**
     * 動態SQL新增資料 (單筆)
     * @param clnt
     * @return
     */
    @Transactional
    public void insert(Clnt clnt) {
        entityCRUEServive.insertEntity(clnt);

        Clnt newClnt = clnt;
        newClnt.setNames("新測試");
        entityCRUEServive.updateEntity(clnt, newClnt);

        entityCRUEServive.deleteEntity(newClnt);

    }


}
