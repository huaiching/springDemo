package com.example.demo.service.Impl;

import com.example.demo.constants.SexEnum;
import com.example.demo.entity.Clnt;
import com.example.demo.service.ClntService;
import com.example.demo.service.WordService;
import com.example.demo.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WordServiceImpl implements WordService {
    @Autowired
    ClntService clntService;

    /**
     * 產出 客戶資料明細表
     *
     * @param clientId
     * @return
     */
    @Override
    public byte[] exportDemo01(String clientId) {
        Clnt clnt = clntService.findById(clientId).get();

        Map<String, Object> context = new HashMap<>();
        context.put("names", clnt.getNames());
        context.put("clientId", clnt.getClientId());
        context.put("birthDate", clnt.getBirthDate());
        context.put("sex", SexEnum.getEnum(clnt.getSex()).getDesc());

        return FileUtil.generateWord("/templates/demo_01.docx", context);
    }
}
