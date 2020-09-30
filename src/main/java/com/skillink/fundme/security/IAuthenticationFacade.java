package com.skillink.fundme.security;

import org.springframework.security.core.Authentication;

/**
 * @author Rabiu Ademoh
 */
public interface IAuthenticationFacade {

    Authentication getAuthentication();
}
