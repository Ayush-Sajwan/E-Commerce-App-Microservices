package com.product.response;

public class ApiResponse<T>{
    String message;
    T data;

    public ApiResponse(String message, T data){
        this.message=message;
        this.data=data;
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
