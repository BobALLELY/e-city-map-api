package com.abstudio.crimecity.api.exception;

/**
 * NafNotFoundException class
 */
public class NotFoundException extends BusinessException {

    /**
     * Constructor NafNotFoundException
     *
     * @param message
     * @param cause
     */
    public NotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor NafNotFoundException
     *
     * @param message
     */
    public NotFoundException(final String message) {
        super(message);
    }
}
