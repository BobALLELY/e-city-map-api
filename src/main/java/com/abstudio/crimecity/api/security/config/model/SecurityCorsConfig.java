package com.abstudio.crimecity.api.security.config.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class SecurityCorsConfig {
    @NotNull
    private String[] allowedOrigins;
    @NotNull
    private String[] allowedMethods;
}
