package com.abstudio.crimecity.api.service.izanami.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Configuration
@Getter
@Setter
public class FeatureFlippingConfig {
    @Valid
    @NotNull
    @Getter
    @Value("${application.featureFlipping.client}")
    private String clientFeature;
}
