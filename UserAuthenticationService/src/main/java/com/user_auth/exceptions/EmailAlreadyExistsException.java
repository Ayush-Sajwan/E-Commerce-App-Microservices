package com.user_auth.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{

    private String errorMessage;

    public EmailAlreadyExistsException(String errorMessage){
        super(errorMessage);
        this.errorMessage=errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
