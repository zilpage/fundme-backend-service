package com.skillink.fundme.exception;


public class BadRequestException extends AbstractException{

    public BadRequestException(String code, String message){
        super(code,message);
    }
}