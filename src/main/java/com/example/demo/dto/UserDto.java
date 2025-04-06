package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserDto {
    @Schema(description = "客戶證號")
    private String clientId;
    @Schema(description = "姓名")
    private String names;
    @Schema(description = "出生日期")
    private String birthDate;
    @Schema(description = "性別")
    private String sex;
    @Schema(description = "手機號碼")
    private String phone;
    @Schema(description = "E-mail")
    private String email;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
