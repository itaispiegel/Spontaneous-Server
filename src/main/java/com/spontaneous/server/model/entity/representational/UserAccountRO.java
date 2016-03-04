package com.spontaneous.server.model.entity.representational;

import com.spontaneous.server.model.entity.Gender;
import org.joda.time.DateTime;

/**
 * This entity represents a user account.
 */
public class UserAccountRO {

    private final long id;

    private final String name;
    private final String email;
    private final String profilePicture;
    private final DateTime birthday;
    private final String gcmToken;
    private final Gender gender;
    private final String phoneNumber;

    public UserAccountRO(long id, String name, String email, String profilePicture, DateTime birthday, String gcmToken, Gender gender, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profilePicture = profilePicture;
        this.birthday = birthday;
        this.gcmToken = gcmToken;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
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
