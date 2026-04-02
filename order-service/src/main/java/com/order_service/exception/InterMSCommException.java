package com.order_service.exception;

public class InterMSCommException extends RuntimeException{

    String msg;

    public InterMSCommException(String msg){
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
