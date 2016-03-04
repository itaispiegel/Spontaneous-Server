package com.spontaneous.server.model.entity.representational;

import com.spontaneous.server.model.entity.Gender;
import com.spontaneous.server.model.entity.User;
import org.joda.time.DateTime;

import java.util.List;

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
    private final String facebookToken;
    private final String facebookUserId;

    private final Gender gender;
    private final String phoneNumber;
    private final List<UserProfileRO> friends;

    public UserAccountRO(User user, List<UserProfileRO> friends) {
        this.id = user.getId();

        this.name = user.getName();
        this.email = user.getEmail();
        this.profilePicture = user.getProfilePicture();
        this.birthday = user.getBirthday();

        this.gcmToken = user.getGcmToken();
        this.facebookToken = user.getFacebookToken();
        this.facebookUserId = user.getFacebookUserId();

        this.gender = user.getGender();
        this.phoneNumber = user.getPhoneNumber();

        this.friends = friends;
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

    public String getFacebookToken() {
        return facebookToken;
    }

    public String getFacebookUserId() {
        return facebookUserId;
    }

    public Gender getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<UserProfileRO> getFriends() {
        return friends;
    }
}
