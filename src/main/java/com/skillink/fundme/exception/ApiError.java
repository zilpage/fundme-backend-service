package com.skillink.fundme.exception;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiError {

	private String code;
    private String message;
    private String description;
    private Instant timestamp;
    private List<String> errors = new ArrayList<>();

    public void addError(String message) { errors.add(message); }
    public boolean hasErrors() {
      return ! errors.isEmpty();
    }
    
    public ApiError() {
    }

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public ApiError(String code, String message) {
        this.code = code;
        this.message = message;
        this.description = message;
        this.timestamp = Instant.now();
    }

    public ApiError(String code, String message, Instant timestamp) {
        this.code = code;
        this.message = message;
        this.description = message;
        this.timestamp = timestamp;
    }
}