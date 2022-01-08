package com.abstudio.crimecity.api.security.config;

import com.abstudio.crimecity.api.security.config.model.SecurityClaimsConfig;
import com.abstudio.crimecity.api.security.config.model.SecurityCorsConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix = "application.security")
@Getter
@ToString
public class SecurityConfig {
    @Value("${application.isProd}")
    private boolean isProd;
    @Valid
    @NotNull
    @Setter
    private SecurityCorsConfig cors;
    @Valid
    @NotNull
    @Setter
    private SecurityClaimsConfig claims;
}


