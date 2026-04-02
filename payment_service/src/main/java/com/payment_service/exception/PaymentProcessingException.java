package com.payment_service.exception;


public class PaymentProcessingException extends RuntimeException {

    String msg;

    public PaymentProcessingException(String msg){
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
