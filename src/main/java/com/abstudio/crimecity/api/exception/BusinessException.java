package com.abstudio.crimecity.api.exception;

/**
 * Business exception
 */
public class BusinessException extends RuntimeException {

    /**
     * Constructor BusinessException
     *
     * @param message
     * @param cause
     */
    public BusinessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor BusinessException
     *
     * @param message
     */
    public BusinessException(final String message) {
        super(message);
    }
}
