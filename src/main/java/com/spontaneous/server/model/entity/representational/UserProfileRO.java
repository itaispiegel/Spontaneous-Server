package com.spontaneous.server.model.entity.representational;

/**
 * This is a representational object of a public user profile.
 */
public class UserProfileRO {

    private final long id;

    private final String name;
    private final String email;
    private final String profilePicture;
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
