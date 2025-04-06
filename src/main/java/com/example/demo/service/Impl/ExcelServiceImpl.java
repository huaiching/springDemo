package com.example.demo.service.Impl;

import com.example.demo.entity.Addr;
import com.example.demo.entity.Clnt;
import com.example.demo.service.AddrService;
import com.example.demo.service.ClntService;
import com.example.demo.service.ExcelService;
import com.example.demo.util.FileUtil;
import org.jxls.common.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {
    @Autowired
    ClntService clntService;
    @Autowired
    AddrService addrService;


    /**
     * 產出 客戶地址明細表
     *
     * @param clientId
     * @return
     */
    @Override
    public byte[] exportDemo02(String clientId) {
        List<Addr> addrList = addrService.findByClientId(clientId);
        Clnt clnt = clntService.findById(clientId).get();

        // 內容物件
        Context context = new Context();
        context.putVar("names", clnt.getNames());
        context.putVar("addr", addrList);
        context.putVar("totle", addrList.size());

        return FileUtil.generateExcel("/templates/demo_02.xlsx", context);
    }

    /**
     * 產出 客戶地址明細表 (排序)
     *
     * @param clientIdList
     * @return
     */
    @Override
    public byte[] exportDemo08(List<String> clientIdList) {
        List<Addr> addrList = new ArrayList<>();

        for (String clientId : clientIdList) {
            List<Addr> addrListByClientId = addrService.findByClientId(clientId);

            addrList.addAll(addrListByClientId);
        }

        // 內容物件
        Context context = new Context();
        context.putVar("addr", addrList);

        return FileUtil.generateExcel("/templates/demo_04.xlsx", context);
    }
}
