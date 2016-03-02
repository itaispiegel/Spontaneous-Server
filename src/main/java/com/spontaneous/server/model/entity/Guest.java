package com.spontaneous.server.model.entity;

import com.google.gson.annotations.Expose;
import com.spontaneous.server.model.entity.representational.GuestRO;
import com.spontaneous.server.model.request.UpdateGuestRequest;

import javax.persistence.*;
import java.util.List;

/**
 * This class represents a user invited to an event.
 * This object contains data of the user specific to the event, and a reference to the user itself.
 * NOTE: This class does not extend the {@code User} class.
 */
@Entity
@Table(name = "guests")
public class Guest extends BaseEntity {

    /**
     * Reference to the user itself.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @Expose
    private User user;

    /**
     * Reference to the event the user is going to.
     * Each event has many guests.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    /**
     * User status in the event.
     */
    @Column(name = "status")
    @Expose
    private String status;

    /**
     * Is the user attending?
     */
    @Column(name = "is_attending")
    @Expose
    private boolean isAttending;

    /**
     * A list of items the guest has committed to bring to the event.
     */
    @OneToMany(mappedBy = "bringer", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Expose
    private List<Item> items;

    /**
     * Default constructor.
     */
    public Guest() {
        this.status = "";
        this.isAttending = false;
    }

    public Guest(User user, Event event) {
        this();
        this.user = user;
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    /**
     * @return Event reference.
     */
    public Event getEvent() {
        return event;
    }

    /**
     * @return user status on the event.
     * <br/>e.g: "Looking forward for it!", "It's gonna be LEGENDARY" and etc.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return Whether the user is attending the event.
     */
    public boolean isAttending() {
        return isAttending;
    }

    public List<Item> getItems() {
        return items;
    }

    /**
     * Update the Guest according to the given {@link UpdateGuestRequest}.
     *
     * @param updateRequest The request to update the invited user.
     */
    public void update(UpdateGuestRequest updateRequest) {
        this.isAttending = updateRequest.isAttending();
        this.status = updateRequest.getStatus();
    }

    @Override
    public String toString() {
        return "Guest{" +
                "user=" + user +
                ", status='" + status + '\'' +
                ", isAttending=" + isAttending +
                '}';
    }

    public GuestRO createRepresentationalObject() {
        return new GuestRO(user, status, isAttending, items);
    }
}
