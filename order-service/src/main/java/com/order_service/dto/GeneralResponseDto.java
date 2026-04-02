package com.order_service.dto;

public class GeneralResponseDto<T> {
    String message;
    T data;

    public GeneralResponseDto(){
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
