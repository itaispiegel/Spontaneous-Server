package com.spontaneous.server.model.entity;

import com.spontaneous.server.model.entity.representational.GuestRO;
import com.spontaneous.server.model.request.UpdateGuestRequest;

import javax.persistence.*;
import java.util.ArrayList;
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
    @ManyToOne(fetch = FetchType.EAGER)
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
    private String status;

    /**
     * Is the user attending?
     */
    @Column(name = "is_attending")
    private boolean isAttending;

    /**
     * A list of items the guest has committed to bring to the event.
     */
    @OneToMany(mappedBy = "bringer", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
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
     * Add an item to the guest's list of items.
     *
     * @param item Item to add to the list.
     */
    public void addItem(Item item) {
        //If items list is null, initialize it.
        if (items == null) {
            items = new ArrayList<>();
        }

        items.add(item);
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
        return new GuestRO(getId(), user, status, isAttending, items);
    }
}
