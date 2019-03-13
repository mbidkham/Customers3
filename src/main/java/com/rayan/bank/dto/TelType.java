package com.rayan.bank.dto;

public enum TelType {
            MOBILE("همراه"), TEL("ثابت");
    private String value;

    TelType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
