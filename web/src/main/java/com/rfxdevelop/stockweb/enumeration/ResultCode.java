package com.rfxdevelop.stockweb.enumeration;

public enum ResultCode {
    SUCCESS(0,"成功"),FAIL(1,"失败"),EXCETION(2,"系统异常");

    private int code;
    private String description;
    ResultCode(int code,String description){
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
