package com.spontaneous.server.model.entity;

/**
 * This enum represents a user's gender.
 */
public enum Gender {
    Male, Female, Unspecified;

    /**
     * Uppercase the first letter so the string will match the enum pattern (Male, Female, Unspecified).
     *
     * @param gender Given gender String to parse.
     * @return Gender enum based on the given String value.
     */
    public static Gender parse(String gender) {
        if (gender == null || gender.length() == 0) {
            return Gender.Unspecified;
        }

        return Gender.valueOf(gender.substring(0, 1).toUpperCase() + gender.substring(1));
    }
}