/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skillink.fundme.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by Ikechukwu.Onyekanna on 11/06/2018.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

	private String code;
    private String description;
    private List<Error> errors;
    private long id;

    public Response() {
    }

    public Response(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the errors
     */
    public List<Error> getErrors() {
        return errors;
    }

    /**
     * @param errors the errors to set
     */
    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
