package com.skillink.fundme.exception;

public class DuplicateException extends AbstractException{

    public DuplicateException(String code, String message){
        super(code,message);
    }
}
