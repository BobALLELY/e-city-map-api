package com.abstudio.crimecity.api.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Configuration for VL app
 */
@Slf4j
@Configuration
@NoArgsConstructor
public class ApplicationConfig {
    @Getter
    @Value("${application.isProd}")
    private boolean isProd;

    @Getter
    @Valid
    @NotNull
    @Value("${spring.application.name}")
    private String applicationName;

    @Getter
    @Valid
    @NotNull
    @Value("${application.apiName}")
    private String apiName;

    @Bean
    public OkHttpClient getOkHttpClient() throws NoSuchAlgorithmException, KeyManagementException {
        // interceptor
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        // create OkHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder
                .callTimeout(Duration.ofMinutes(1)) // default timeout for complete calls
                .readTimeout(Duration.ofMinutes(1)) // default read timeout for new connections
                .writeTimeout(Duration.ofMinutes(1)) // default write timeout for new connections
                .retryOnConnectionFailure(true) // retry or not when a connectivity problem is encountered
                .addInterceptor(loggingInterceptor); // add request interceptor


        return builder.build();
    }
}
