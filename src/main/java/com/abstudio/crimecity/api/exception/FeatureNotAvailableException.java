package com.abstudio.crimecity.api.exception;

/**
 * Feature not available exception
 */
public class FeatureNotAvailableException extends RuntimeException {

    /**
     * Constructor FeatureNotAvailableException
     *
     * @param message
     * @param cause
     */
    public FeatureNotAvailableException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor FeatureNotAvailableException
     *
     * @param message
     */
    public FeatureNotAvailableException(final String message) {
        super(message);
    }
}
