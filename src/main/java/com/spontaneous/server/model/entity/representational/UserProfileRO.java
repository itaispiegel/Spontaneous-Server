package com.spontaneous.server.model.entity.representational;

import com.google.gson.annotations.Expose;

/**
 * This is a representational object of a public user profile.
 */
public class UserProfileRO {

    /**
     * Id of the user.
     */
    @Expose
    private final long id;

    /**
     * User full name;
     */
    @Expose
    private final String name;

    /**
     * Email of the user.
     */
    @Expose
    private final String email;

    /**
     * Profile picture URL of the user.
     */
    @Expose
    private final String profilePicture;

    /**
     * Age of the user in years.
     */
    @Expose
    private final int age;

    public UserProfileRO(long id, String name, String email, String profilePicture, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profilePicture = profilePicture;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public int getAge() {
        return age;
    }
}
