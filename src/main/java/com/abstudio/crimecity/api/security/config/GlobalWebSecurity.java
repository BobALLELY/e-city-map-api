package com.abstudio.crimecity.api.security.config;

import com.abstudio.crimecity.api.config.ApplicationConfig;
import com.abstudio.crimecity.api.security.auth.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class GlobalWebSecurity extends WebSecurityConfigurerAdapter {
    private final ApplicationConfig applicationConfig;
    private final SecurityConfig securityConfig;

    /**
     * Bean to configure CORS
     */
    CorsConfigurationSource corsConfiguration() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.applyPermitDefaultValues();
        corsConfig.setAllowedOrigins(Arrays.asList(this.securityConfig.getCors().getAllowedOrigins()));
        corsConfig.setAllowedMethods(Arrays.asList(this.securityConfig.getCors().getAllowedMethods()));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

    /**
     * Configuration of the filters the execute before reaching the controllers
     *
     * @param security Security
     */
    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .cors().configurationSource(this.corsConfiguration()).and()
                .csrf().disable() // cf. https://stackoverflow.com/questions/50486314/how-to-solve-403-error-in-spring-boot-post-request
                .httpBasic().disable()
                .formLogin().disable();

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry matchers =
                security.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and()
                        .authorizeRequests()
                        .antMatchers(HttpMethod.OPTIONS).permitAll()
                        .antMatchers("/favicon.ico").permitAll()
                        .antMatchers("/" + this.applicationConfig.getApiName() + "/**").permitAll()
                        .antMatchers("/health/**").permitAll()
                        .antMatchers("/manage/**").hasRole(Role.ADMIN.name());

        // authorize in non production
        if (!this.securityConfig.isProd()) {
            matchers.antMatchers("/swagger-ui/**").permitAll()
                    .antMatchers("/info").permitAll()
                    .antMatchers("/v3/api-docs/**").permitAll()
                    .antMatchers("/crime-city/**").permitAll();
        }

//        matchers
//                .anyRequest().denyAll()
//                .and()
//                .oauth2ResourceServer()
//                .bearerTokenResolver(this.securityBearerTokenResolver)
//                .authenticationManagerResolver(this.securityAuthenticationManagerResolver);

    }
}
