package com.webshop.tokyolife.model.enums;

public enum AuthResponActionEnum {
    REGISTER(1);

    private final int value;
    AuthResponActionEnum(int value){
        this.value = value;
    }
    public int getValue() {return value;}
}
