package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class EntityWhereDto {
    @Schema(description = "欄位名稱")
    private String columnName;
    @Schema(description = "查詢操作符")
    private String operator;
    @Schema(description = "查詢值")
    private Object value;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
