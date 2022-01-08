package com.abstudio.crimecity.api.helper;

import com.abstudio.crimecity.api.security.IUserAuthentication;
import com.abstudio.crimecity.api.security.auth.user.UserAuthentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.UUID;

/**
 * Helper class used to retreive UserContexte
 */
public final class UserHelper {

    private UserHelper() {
    }

    /**
     * Get User Context from Authentication Security Context
     *
     * @return User Context
     */
    public static IUserAuthentication getUserContext() {
        return (IUserAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

    public static IUserAuthentication getMockUserContext() {
        return new UserAuthentication(UUID.randomUUID().toString());
    }
}
