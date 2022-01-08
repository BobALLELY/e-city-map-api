package com.abstudio.crimecity.api.helper;


import lombok.Getter;

/**
 * Define constants needed by the application
 */
public final class Constants {

    private Constants() {
    }

    public static enum LANGUAGE {
        FR("fr"),
        EN("en");

        @Getter
        private String value;

        LANGUAGE(String value) {
            this.value = value;
        }
    }

}
