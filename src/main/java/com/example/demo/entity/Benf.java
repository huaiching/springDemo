package com.example.demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.*;

//@Table(name = "benf")
@Schema(description = "受益人檔")
public class Benf {
    @Schema(description = "保單號碼")
//    @Column(name = "policy_no", length = 12, nullable = false)
    private String policyNo;

    @Schema(description = "關係碼")
//    @Column(name = "relation", length = 1, nullable = false)
    private String relation;

    @Schema(description = "客戶證號")
//    @Column(name = "client_id", length = 10)
    private String clientId;

    @Schema(description = "姓名")
//    @Column(name = "names", length = 100)
    private String names;

    // Getters 和 Setters
    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }
}
