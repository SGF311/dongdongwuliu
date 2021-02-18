package com.dongdongwuliu.data;


/**
 * @Deacription TODO
 * @Author ASUS
 * @Date 2020/12/9 20:22
 * @Version 1.0
 **/
public class DataResult<T> {
    private Integer code;// 状态码
    private String message;// 响应信息
    private T data;// 响应数据

    public static DataResult response(com.dongdongwuliu.data.ResponseStatusEnum responseStatusEnum){
        return new DataResult(responseStatusEnum.getCode(),responseStatusEnum.getMessage());
    }

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

    public T getData() {
        return data;
    }

    public DataResult setData(T data) {
        this.data = data;
        return this;
    }

    public DataResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public DataResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public DataResult() {
    }
}
