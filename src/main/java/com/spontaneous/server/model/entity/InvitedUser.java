package com.spontaneous.server.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * This class represents a user invited to an event.
 */

@Entity
@Table(name = "invited_users")
public class InvitedUser extends BaseEntity {

    /**
     * The user data.
     */
    @OneToOne
    private User user;

    /**
     * User status in the event.
     */
    @Column(name = "status")
    private String status;

    /**
     * Is the user attending?
     */
    @Column(name = "is_attending")
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
