package com.skillink.fundme.exception;

import org.springframework.security.core.AuthenticationException;


public class ChangePasswordException extends AuthenticationException {
    public ChangePasswordException(String msg) {
        super(msg);
    }

    public ChangePasswordException(String msg, Throwable t) {
        super(msg, t);
    }
}
