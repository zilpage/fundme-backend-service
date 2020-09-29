package com.skillink.fundme.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.skillink.fundme.exception.ApiError;
import com.skillink.fundme.exception.BadRequestException;
import com.skillink.fundme.exception.ChangePasswordException;
import com.skillink.fundme.exception.DuplicateException;
import com.skillink.fundme.exception.NotFoundException;
import com.skillink.fundme.exception.UserLoginException;



@ResponseBody
@ControllerAdvice(annotations = RestController.class, basePackages = "com.skillink.fundme")
public class ApiAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ApiAdvice.class);
    
    @ExceptionHandler(UserLoginException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ApiError handleLoginException(UserLoginException e) {
    	ApiError response = new ApiError();
        response.setCode(e.getCode());
        response.setDescription(e.getMessage());
        return response;
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleNotFoundException(BadRequestException e) {
    	ApiError response = new ApiError();
        response.setCode(e.getCode());
        response.setDescription(e.getMessage());
        return response;
    }
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ApiError handleBadCredentialException(BadCredentialsException e) {
    	ApiError response = new ApiError();
        response.setCode("10009");
        response.setDescription(e.getMessage());
        return response;
    }
    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleServletRequestBindingException(ServletRequestBindingException e) {
    	ApiError response = new ApiError();
        response.setCode("10011");
        response.setDescription("There is an error with your request.Please refer to docs");
       
        return response;
    }

    @ExceptionHandler(ChangePasswordException.class)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @ResponseBody
    public ApiError handleChangePasswordException(ChangePasswordException e) {
    	ApiError response = new ApiError();
        response.setCode("10012");
        response.setDescription("First time login. Change default password");
       // LoggerUtil.logError(logger, e);
        return response;
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiError handleException(Exception e) {
    	
    	ApiError response = new ApiError();
        response.setCode("9999");
       response.setDescription("An error occurred while processing your request. Please contact server administrator for more details");
     
        return response;
    }
    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ResponseBody
    public ApiError handleCreateUserException(DuplicateException e) {
    	ApiError response = new ApiError();
        response.setCode(e.getCode());
        response.setDescription(e.getMessage());
        return response;
    }
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ResponseBody
    public ApiError handleAccessDeniedException(AccessDeniedException e) {
    	ApiError response = new ApiError();
        response.setCode("10013");
        response.setDescription(e.getMessage());
        return response;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError handleNotFoundException(NotFoundException e) {
    	ApiError response = new ApiError();
        response.setCode(e.getCode());
        response.setDescription(e.getMessage());
        return response;
    }

}
