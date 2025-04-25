package com.example.demo.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(Addr.AddrKey.class)
@Table(name = "addr")
@Schema(description = "客戶地址檔")
public class Addr {
    // 多主鍵 的 主鍵宣告: 下面透過 崁套類 來宣告組件
    @Id
    @Schema(description = "客戶證號")
    @Column(name = "client_id", length = 10, nullable = false)
    private String clientId;

    @Id
    @Schema(description = "地址指示")
    @Column(name = "addr_ind", length = 1, nullable = false)
    private String addrInd;

    @Schema(description = "地址")
    @Column(name = "address", length = 72)
    private String address;

    @Schema(description = "電話")
    @Column(name = "tel_1", length = 11)
    private String tel1;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAddrInd() {
        return addrInd;
    }

    public void setAddrInd(String addrInd) {
        this.addrInd = addrInd;
    }

    // Getters 和 Setters
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getTel1() {
        return tel1;
    }
    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    // 崁套類的主鍵宣告
    @Embeddable
    public static class AddrKey implements Serializable {

        @Schema(description = "客戶證號")
        @Column(name = "client_id", length = 10, nullable = false)
        private String clientId;

        @Schema(description = "地址指示")
        @Column(name = "addr_ind", length = 1, nullable = false)
        private String addrInd;

        // 建構式 (JPA 要求)
        public AddrKey() {}

        // Getters 和 Setters
        public String getClientId() {
            return clientId;
        }
        public void setClientId(String clientId) {
            this.clientId = clientId;
        }
        public String getAddrInd() {
            return addrInd;
        }
        public void setAddrInd(String addrInd) {
            this.addrInd = addrInd;
        }

        // equals 和 hashCode（JPA 要求）
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AddrKey addrKey = (AddrKey) o;
            return Objects.equals(clientId, addrKey.clientId) && Objects.equals(addrInd, addrKey.addrInd);
        }
        @Override
        public int hashCode() {
            return Objects.hash(clientId, addrInd);
        }
    }
}
