package com.abstudio.crimecity.api.helper;

import com.abstudio.crimecity.api.security.IUserAuthentication;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class TechnicalValidatorHelper {
    private static final String ARGUMENT_CHECKING = "Argument checking";
    public static final String ERROR_STRING = "{}: {}";

    private TechnicalValidatorHelper() {
    }

    /**
     * User validation
     *
     * @param u {@link IUserAuthentication}
     */
    public static void validateUser(IUserAuthentication u) {
        if (u == null) {
            throw new IllegalArgumentException("user cannot be null");
        }

        TechnicalValidatorHelper.validateString(u.getUserId(), "user id");
    }

    /**
     * string validation
     *
     * @param s             value
     * @param attributeName attribute name
     */
    public static void validateString(String s, String attributeName) {
        if (null == s || s.isBlank()) {
            String err = attributeName + " parameter is empty";
            TechnicalValidatorHelper.log.error(ERROR_STRING, ARGUMENT_CHECKING, err);
            throw new IllegalArgumentException(err);
        }
    }
}
