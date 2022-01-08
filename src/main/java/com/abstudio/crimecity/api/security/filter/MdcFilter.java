package com.abstudio.crimecity.api.security.filter;

import com.abstudio.crimecity.api.security.IUserAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Add to MDC UOM_CODE and USR_ID
 */
@Slf4j
@Order // LOWEST_PRECEDENCE by default
@Component
public class MdcFilter extends GenericFilterBean {
    public static final String USER_ID_LOG = "usr_id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // if user is authenticated, add information to MDC
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof IUserAuthentication) {
            IUserAuthentication gsAuth = (IUserAuthentication) authentication;
            MDC.put(USER_ID_LOG, gsAuth.getUserId());
        }

        chain.doFilter(request, response);

        MDC.remove(USER_ID_LOG);
    }
}
