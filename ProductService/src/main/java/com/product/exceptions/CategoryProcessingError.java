package com.product.exceptions;

public class CategoryProcessingError extends RuntimeException{

    private String msg;

    public CategoryProcessingError(String msg){
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
