package com.spontaneous.server.model.entity;

import com.google.gson.annotations.Expose;
import com.spontaneous.server.model.entity.representational.ItemRO;

import javax.persistence.*;

/**
 * This class represents an item a guest commits to bring to an event.
 */
@Entity
@Table(name = "items")
public class Item extends BaseEntity {

    /**
     * The guest bringing the item.
     * Each guest can bring many items.
     */
    @ManyToOne(targetEntity = Guest.class, fetch = FetchType.LAZY)
    private Guest bringer;

    /**
     * Reference to the event the item is related to.
     * Each event has many items relating.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;

    /**
     * The title name of the item.
     */
    @Column(name = "title")
    @Expose
    private String title;

    /**
     * A boolean indicating whether the guest is bringing the item.
     */
    @Column(name = "is_bringing")
    @Expose
    private boolean isBringing;

    /**
     * Default constructor.
     */
    public Item() {
    }

    public Item(Guest bringer, Event event, String title, boolean isBringing) {
        this.bringer = bringer;
        this.event = event;
        this.title = title;
        this.isBringing = isBringing;
    }

    public Guest getBringer() {
        return bringer;
    }

    public Event getEvent() {
        return event;
    }

    public String getTitle() {
        return title;
    }

    public boolean isBringing() {
        return isBringing;
    }

    public ItemRO createRepresentationalObject() {
        return new ItemRO(title, isBringing);
    }

    @Override
    public String toString() {
        return "Item{" +
                "guest=" + bringer +
                ", event=" + event +
                ", title='" + title + '\'' +
                ", isBringing=" + isBringing +
                '}';
    }
}
