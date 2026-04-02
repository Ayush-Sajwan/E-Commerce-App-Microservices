package com.inventory_service.exception;

public class ControllerRequestException extends RuntimeException {
    String msg;

    public ControllerRequestException(String msg){
        super(msg);
        this.msg=msg;
    }
}
