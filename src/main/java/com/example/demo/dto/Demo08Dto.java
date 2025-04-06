package com.example.demo.dto;


import com.example.demo.entity.Addr;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class Demo08Dto {
    private String names;
    private List<Addr> addrList;

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public List<Addr> getAddrList() {
        return addrList;
    }

    public void setAddrList(List<Addr> addrList) {
        this.addrList = addrList;
    }
}


