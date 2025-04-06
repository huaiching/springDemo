package com.example.demo.dto;


import com.example.demo.entity.Addr;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

public class ZipFlieDto {
    private String flieName;
    private byte[] FileBytes;

    public ZipFlieDto(String flieName, byte[] fileBytes) {
        this.flieName = flieName;
        FileBytes = fileBytes;
    }

    public String getFlieName() {
        return flieName;
    }

    public void setFlieName(String flieName) {
        this.flieName = flieName;
    }

    public byte[] getFileBytes() {
        return FileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        FileBytes = fileBytes;
    }
}


