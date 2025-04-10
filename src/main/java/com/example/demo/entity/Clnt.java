package com.example.demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "clnt")
@Schema(description = "客戶資料檔")
public class Clnt {

    @Id
    @Schema(description = "客戶證號")
    @Column(name = "client_id", length = 10, nullable = false)
    private String clientId;

    @Schema(description = "姓名")
    @Column(name = "names", length = 100)
    private String names;

    @Schema(description = "出生日期")
    @Column(name = "birth_date", length = 9)
    private String birthDate;

    @Schema(description = "性別")
    @Column(name = "sex", length = 2)
    private String sex;

    // Getters 和 Setters
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
