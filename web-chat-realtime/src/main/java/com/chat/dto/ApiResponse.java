package com.chat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

//@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ApiResponse <T>{
    private int code;// oke code = 1000, false: 1001/1002/1003/9999
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

}
