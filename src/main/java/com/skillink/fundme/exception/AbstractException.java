package com.skillink.fundme.exception;


public class AbstractException extends RuntimeException {
    String code;


    public AbstractException(String code, String message){
        super(message);
        this.code = code;
    }
    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }
}
