package com.order_service.exception;

public class OrderProcessingException extends RuntimeException{
    String msg;

    public OrderProcessingException(String msg){
        super(msg);
        this.msg=msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
