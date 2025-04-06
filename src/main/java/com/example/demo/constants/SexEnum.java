package com.example.demo.constants;

public enum SexEnum {
    MAN("1", "男"),
    WOMEN("2","女");

    private String code;
    private String desc;

    SexEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    // getting
    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static SexEnum getEnum(String code) {
        for (SexEnum sexEnum : SexEnum.values()) {
            if (sexEnum.getCode().equals(code)) {
                return sexEnum;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
