package com.abstudio.crimecity.api.security.config.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class SecurityClaimsConfig {
    @NotBlank
    private String userId;
    @NotBlank
    private String userUomCode;
    @NotBlank
    private String userAgencyCode;
    @NotBlank
    private String userProfile;
    @NotBlank
    private String deliveryChannel;
    @NotBlank
    private String userType;
}
