package com.inventory_service.exception;

public class InventoryProcessingException extends  RuntimeException{
    String msg;

    public InventoryProcessingException(String msg){
        super(msg);
        this.msg=msg;
    }
}
