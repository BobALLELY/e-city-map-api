package com.abstudio.crimecity.api.security;

public interface IUserAuthentication {
    /**
     * Correlation id from the request header to identify a call throughout multiple applications.
     */
    String getCorrelationId();

    /**
     * User id from the authentication token. It is the id of the user that make the call (e.g. a banking advisor).
     */
    String getUserId();
}
