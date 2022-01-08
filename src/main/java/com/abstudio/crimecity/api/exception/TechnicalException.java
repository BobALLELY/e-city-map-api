package com.abstudio.crimecity.api.exception;

/**
 * Technical exception
 */
public class TechnicalException extends RuntimeException {

    /**
     * Constructor TechnicalException
     *
     * @param message
     * @param cause
     */
    public TechnicalException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor TechnicalException
     *
     * @param message
     */
    public TechnicalException(final String message) {
        super(message);
    }
}
