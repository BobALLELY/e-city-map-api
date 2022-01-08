package com.abstudio.crimecity.api.security.auth.user;

import com.abstudio.crimecity.api.security.IUserAuthentication;
import com.abstudio.crimecity.api.security.config.SecurityConfig;
import lombok.Builder;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@ToString
public class UserAuthentication implements IUserAuthentication {
    @Setter
    private String correlationId;
    private String userId;

    public UserAuthentication(
            String userId
    ) {
        this.userId = userId;
    }

    @Override
    public String getCorrelationId() {
        return this.correlationId;
    }

    @Override
    public String getUserId() {
        return this.userId;
    }
}
