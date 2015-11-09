package com.example.model.entity;

/**
 * This class represents a user invited to an event.
 */
public class InvitedUser {

    /**
     * The user data.
     */
    private User user;

    /**
     * User status in the event.
     */
    private String status;

    /**
     * Is the user attending?
     */
    private Boolean isAttending;

    public InvitedUser() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIsAttending() {
        return isAttending;
    }

    public void setIsAttending(Boolean isAttending) {
        this.isAttending = isAttending;
    }

    @Override
    public String toString() {
        return "InvitedUser{" +
                "user=" + user +
                ", status='" + status + '\'' +
                ", isAttending=" + isAttending +
                '}';
    }
}
