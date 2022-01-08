package com.abstudio.crimecity.api.security.auth;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public enum Role implements GrantedAuthority {
    ANONYMOUS,
    APIM,
    CUSTOMER,
    ADVISOR,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
