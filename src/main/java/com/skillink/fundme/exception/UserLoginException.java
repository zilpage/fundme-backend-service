package com.skillink.fundme.exception;


public class UserLoginException extends AbstractException{

    public UserLoginException(String code, String message){
        super(code,message);
    }
}
