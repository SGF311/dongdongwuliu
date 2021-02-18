package com.dongdongwuliu.data;

public enum ResponseStatusEnum {
    SUCCESS(200,"操作成功"),BAD_REQUEST(400,"参数异常"),
    INTERNAL_SERVER_ERROR(500,"系统错误"),
    USER_NOT_FOUND(600,"找不到用户"),
    PASSWORD_ERROR(700,"密码错误"),
    FAIL(1001,"操作失败");
    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ResponseStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
