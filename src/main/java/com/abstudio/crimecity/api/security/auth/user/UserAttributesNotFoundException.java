package com.abstudio.crimecity.api.security.auth.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;

@RequiredArgsConstructor
public class UserAttributesNotFoundException extends RuntimeException {
    private final String attribute;
    private final Jwt jwt;

    @Override
    public String getMessage() {
        return String.format("Could not find %s in %s", attribute, jwt.getTokenValue());
    }
}
