package com.pojo;

public class CommonResult<T> {
    private T data;

    public CommonResult(T data, String messsage, Integer code) {
        this.data = data;
        this.messsage = messsage;
        this.code = code;
    }

    private String messsage;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    private Integer code;

    public CommonResult(String message, Integer code) {
        this(null, message, code);
    }

    public CommonResult(T data) {
        this(data, "操作成功", 200);
    }

}
