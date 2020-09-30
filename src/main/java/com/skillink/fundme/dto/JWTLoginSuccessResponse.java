package com.skillink.fundme.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.skillink.fundme.dal.entity.Permission;
import com.skillink.fundme.dal.entity.Role;
import com.skillink.fundme.dal.entity.User;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author Rabiu Ademoh
 */

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JWTLoginSuccessResponse implements Serializable {

    private String lastLogin;

    @JsonProperty("AccessToken")
    private String accessToken;

    @JsonProperty("Username")
    private String username;


    private boolean otp;
    @JsonProperty("Id")
    private Long id;
    @JsonProperty("Role")
    private Role role;

    @JsonProperty("Permissions")
    private Collection<Permission> permissions;

    @JsonProperty("User")
    private User user;

    public JWTLoginSuccessResponse(String lastLogin, String accessToken, String username, Role role, Collection<Permission> permissions) {
        this.lastLogin = lastLogin;
        this.accessToken = accessToken;
        this.username = username;
        this.role = role;
        this.permissions = permissions;

    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isOtp() {
        return otp;
    }

    public void setOtp(boolean otp) {
        this.otp = otp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Collection<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Collection<Permission> permissions) {
        this.permissions = permissions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
