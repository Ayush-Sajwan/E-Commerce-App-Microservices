package com.product.exceptions;

public class ProductNotFoundException extends RuntimeException{

    private String msg;
    public ProductNotFoundException(String msg){
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
