package com.spontaneous.server.model.entity.representational;

import com.google.gson.annotations.Expose;
import com.spontaneous.server.model.entity.Gender;
import org.joda.time.DateTime;

/**
 * This entity represents a user account.
 */
public class UserAccountRO {

    @Expose
    private final String name;

    @Expose
    private final String email;

    @Expose
    private final String profilePicture;

    @Expose
    private final DateTime birthday;

    @Expose
    private final String gcmToken;

    @Expose
    private final Gender gender;

    @Expose
    private final String phoneNumber;

    public UserAccountRO(String name, String email, String profilePicture, DateTime birthday, String gcmToken, Gender gender, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.profilePicture = profilePicture;
        this.birthday = birthday;
        this.gcmToken = gcmToken;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
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

    public DateTime getBirthday() {
        return birthday;
    }

    public String getGcmToken() {
        return gcmToken;
    }

    public Gender getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
