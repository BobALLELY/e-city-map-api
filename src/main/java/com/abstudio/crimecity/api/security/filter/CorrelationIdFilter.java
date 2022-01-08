package com.abstudio.crimecity.api.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * Add correlation id in request context
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class CorrelationIdFilter extends GenericFilterBean {

    public static final String CORRELATION_ID_HEADER_1 = "x-correlation-id";
    public static final String CORRELATION_ID_HEADER_2 = "Correlationid";
    private static final String CORRELATION_ID_LOG = "corr_id";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        // Instantiation of a "custom" HttpServletRequest in order to be able, if necessary, to add a Header
        final CustomHttpServletRequest custHttpRequest = new CustomHttpServletRequest((HttpServletRequest) servletRequest);
        // Retrieving the value of the "x-correlation-id" Header
        String correlationIdValue;
        // If the "x-correlation-id" Header is valued ...
        if (!StringUtils.isBlank(custHttpRequest.getHeader(CORRELATION_ID_HEADER_1))) {
            correlationIdValue = custHttpRequest.getHeader(CORRELATION_ID_HEADER_1);
        }
        // If the "Correlationid" Header is valued ...
        else if (!StringUtils.isBlank(custHttpRequest.getHeader(CORRELATION_ID_HEADER_2))) {
            correlationIdValue = custHttpRequest.getHeader(CORRELATION_ID_HEADER_2);
        } else {
            // ... So, Generating a Correlation Identifier from a UUID
            correlationIdValue = UUID.randomUUID().toString();
            // Addition of the "x-correlation-id" Header with the generated UUID value
            custHttpRequest.addHeader(CORRELATION_ID_HEADER_1, correlationIdValue);
        }

        MDC.put(CORRELATION_ID_LOG, correlationIdValue);
        chain.doFilter(custHttpRequest, servletResponse);
        MDC.remove(CORRELATION_ID_LOG);
    }
}
