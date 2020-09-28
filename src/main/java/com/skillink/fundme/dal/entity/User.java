package com.skillink.fundme.dal.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL) 
@Entity
@Table(name = "tbl_user")
public class User implements Serializable {

   
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String firstName;
	private String lastName;
	private String username;
    private String emailAddress;
    private boolean is_enabled;
    private long roleId;
    private String roleName;
    private String phoneNumber;
    private String password;
    private String lastLogin;
    private String status;
    private int loginFailedCount;
    private boolean is_locked;
    
    @Transient
    private Collection<Permission> permissions;
    @Transient
    private Role role;

	
	
	
}