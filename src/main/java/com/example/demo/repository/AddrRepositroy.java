package com.example.demo.repository;

import com.example.demo.entity.Addr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddrRepositroy extends JpaRepository<Addr, Addr.AddrKey> {
    /**
     * SELECT * FROM addr
     * WHERE client_id = :clientId
     */
    List<Addr> findByClientId(String clientId);

    /**
     * SELECT * FROM addr
     * WHERE client_id = :clientId
     * ORDER BY addr_ind DESC
     */
    List<Addr> findByClientIdOrderByAddrIndDesc(String clientId);
}
