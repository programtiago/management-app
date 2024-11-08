package com.netceed.management.management_app.exception;

public class EmailAlreadyExistsException extends NoSuchFieldException{

    public EmailAlreadyExistsException(String message){
        super(message);
    }
}
