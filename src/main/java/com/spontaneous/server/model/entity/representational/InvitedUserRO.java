package com.spontaneous.server.model.entity.representational;

/**
 * Created by Itai on 27-Feb-16.
 */
public class InvitedUserRO {

    private final UserProfileRO userProfile;
    private final EventRO event;
    private final String status;
    private final boolean isAttending;

    public InvitedUserRO(UserProfileRO userProfile, EventRO event, String status, boolean isAttending) {
        this.userProfile = userProfile;
        this.event = event;
        this.status = status;
        this.isAttending = isAttending;
    }

    public UserProfileRO getUserProfile() {
        return userProfile;
    }

    public EventRO getEvent() {
        return event;
    }

    public String getStatus() {
        return status;
    }

    public boolean isAttending() {
        return isAttending;
    }
}
