package com.example.demo.service.Impl;

import com.example.demo.entity.Addr;
import com.example.demo.mapper.AddrMapper;
import com.example.demo.repository.AddrRepositroy;
import com.example.demo.service.AddrService;
import javax.persistence.EntityManager;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddrServiceImpl implements AddrService {
    @Autowired
    AddrRepositroy addrRepositroy;
    @Autowired
    AddrMapper addrMapper;
    @Autowired
    EntityManager entityManager;

    /**
     * 新增/修改 資料 (單筆)
     *
     * @param addr
     * @return
     */
    @Transactional
    public Addr save(Addr addr) {
        return addrRepositroy.save(addr);
    }

    /**
     * 新增/修改 資料 (多筆)
     *
     * @param addrList
     * @return
     */
    @Transactional
    public List<Addr> saveAll(List<Addr> addrList) {
        return addrRepositroy.saveAll(addrList);
    }

    /**
     * 根據 主鍵(clientId) 查詢資料
     *
     * @param addrKey clientId + addrInd
     * @return
     */
    @Override
    public Addr findById(Addr.AddrKey addrKey) {
        return addrRepositroy.findById(addrKey).get();
    }

    /**
     * 根據 主鍵集合(clientId) 查詢資料
     *
     * @param addrKeyList clientId + addrInd 集合
     * @return
     */
    @Override
    public List<Addr> findAllById(List<Addr.AddrKey> addrKeyList) {
        return addrRepositroy.findAllById(addrKeyList);
    }

    /**
     * 根據 主鍵(clientId) 刪除 資料
     *
     * @param addrKey clientId + addrInd
     */
    @Transactional
    public void deleteById(Addr.AddrKey addrKey) {
        addrRepositroy.deleteById(addrKey);

    }

    /**
     * 根據 主鍵集合(clientId) 刪除 資料
     *
     * @param addrKeyList clientId + addrInd 集合
     */
    @Transactional
    public void deleteAllById(List<Addr.AddrKey> addrKeyList) {
        addrRepositroy.deleteAllById(addrKeyList);
    }

    /**
     * 根據 客戶證號 查詢資料
     *
     * @param clientId
     * @return
     */
    @Override
    public List<Addr> findByClientId(String clientId) {
        String sql = "SELECT * FROM addr " +
                "WHERE client_id = :clientId " +
                "ORDER BY addr_ind ";

        return entityManager.createNativeQuery(sql, Addr.class)
                .unwrap(NativeQuery.class)
                .setParameter("clientId", clientId)
                .getResultList();
    }

    /**
     * 取得 E指示
     *
     * @param clientId
     * @return
     */
    @Override
    public Addr findEAddrByClientId(String clientId) {
        String sql = "SELECT * FROM addr " +
                "WHERE client_id = :clientId " +
                "  AND addr_ind = 'E' ";
        List<Addr> addrList = entityManager.createNativeQuery(sql, Addr.class)
                .unwrap(NativeQuery.class)
                .setParameter("clientId", clientId)
                .getResultList();
        return CollectionUtils.isEmpty(addrList) ? new Addr() : addrList.get(0);
    }
}
