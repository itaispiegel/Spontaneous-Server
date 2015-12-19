package com.spontaneous.server.model.entity;

import javax.persistence.*;

/**
 * This class represents a user invited to an event.
 * This object contains data of the user specific to the event, and a reference to the user itself.
 */
@Entity
@Table(name = "invited_users")
public class InvitedUser extends BaseEntity {

    /**
     * Reference to the user itself.
     */
    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    /**
     * Reference to the event the user is going to.
     */
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

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

    /**
     * Create an empty invited user.
     */
    public InvitedUser() {
    }

    /**
     * @return User reference.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user reference.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return Event reference.
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Sets the event reference.
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * @return user status on the event.
     * <br/>e.g: "Looking forward for it!", "It's gonna be LEGENDARY" and etc.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets user status on the event.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return Whether the user is attending the event.
     */
    public Boolean isAttending() {
        return isAttending;
    }

    /**
     * Sets whether the user is attending the event.
     */
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
