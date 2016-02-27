package com.spontaneous.server.model.entity.representational;

import com.spontaneous.server.model.entity.InvitedUser;

/**
 * This is a representational object for the {@link InvitedUser} entity.
 */
public class InvitedUserRO {

    private final UserProfileRO userProfile;
    private final String status;
    private final boolean isAttending;

    public InvitedUserRO(UserProfileRO userProfile, String status, boolean isAttending) {
        this.userProfile = userProfile;
        this.status = status;
        this.isAttending = isAttending;
    }

    public UserProfileRO getUserProfile() {
        return userProfile;
    }

    public String getStatus() {
        return status;
    }

    public boolean isAttending() {
        return isAttending;
    }
}
