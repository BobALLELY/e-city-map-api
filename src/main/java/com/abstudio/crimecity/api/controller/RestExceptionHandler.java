package com.abstudio.crimecity.api.controller;

import com.abstudio.crimecity.api.exception.BusinessException;
import com.abstudio.crimecity.api.exception.FeatureNotAvailableException;
import com.abstudio.crimecity.api.exception.NotFoundException;
import com.abstudio.crimecity.api.exception.TechnicalException;
import com.abstudio.crimecity.api.service.metric.RestMetricService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * RestExceptionHandler class
 */
@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String ERROR_OCCURED = "An error occurred";
    @Autowired
    RestMetricService metricService;

    /**
     * Business exception handler
     *
     * @return the exceptions
     */
    @ExceptionHandler({
            BusinessException.class,
            IllegalArgumentException.class
    })
    protected ResponseEntity<Object> handleBusinessErrorRequest(Exception ex, WebRequest request) {
        RestExceptionHandler.log.warn(ex.getLocalizedMessage());
        this.metricService.increaseStatusRequest(HttpStatus.BAD_REQUEST.value());
        return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Not found exception handler
     *
     * @return the exceptions
     */
    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFoundRequest(Exception ex, WebRequest request) {
        RestExceptionHandler.log.warn(ex.getLocalizedMessage());
        this.metricService.increaseStatusRequest(HttpStatus.NOT_FOUND.value());
        return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    /**
     * Access denied exception handler
     *
     * @return the exceptions
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeniedRequest(Exception ex, WebRequest request) {
        RestExceptionHandler.log.warn(ex.getLocalizedMessage());
        this.metricService.increaseStatusRequest(HttpStatus.UNAUTHORIZED.value());
        return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    /**
     * Forbidden exception handler
     *
     * @return the exceptions
     */
    @ExceptionHandler(FeatureNotAvailableException.class)
    protected ResponseEntity<Object> handleForbiddenRequest(Exception ex, WebRequest request) {
        RestExceptionHandler.log.warn(ex.getLocalizedMessage());
        this.metricService.increaseStatusRequest(HttpStatus.FORBIDDEN.value());
        return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    /**
     * Technical exception handler
     *
     * @return the exceptions
     */
    @ExceptionHandler({
            TechnicalException.class,
            Exception.class,
            Error.class
    })
    public ResponseEntity<Object> handleServiceErrorRequest(Exception ex, WebRequest request) {
        RestExceptionHandler.log.error(ex.getLocalizedMessage(), ex);
        this.metricService.increaseStatusRequest(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return handleExceptionInternal(ex, ERROR_OCCURED, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
