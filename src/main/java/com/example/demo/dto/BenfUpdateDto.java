package com.example.demo.dto;

import com.example.demo.entity.Benf;
import io.swagger.v3.oas.annotations.media.Schema;

public class BenfUpdateDto {
    @Schema(description = "修改前 Benf")
    private Benf oldBenf;
    @Schema(description = "修改後 Benf")
    private Benf newBenf;

    public Benf getOldBenf() {
        return oldBenf;
    }

    public void setOldBenf(Benf oldBenf) {
        this.oldBenf = oldBenf;
    }

    public Benf getNewBenf() {
        return newBenf;
    }

    public void setNewBenf(Benf newBenf) {
        this.newBenf = newBenf;
    }
}
