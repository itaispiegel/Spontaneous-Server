package com.example.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * This class represents an event.
 */
public class Event {

    /**
     * The id of the event.
     */
    @Id
    @GeneratedValue
    private long id;

    /**
     * The title of the event.
     */
    @Column(name = "title")
    private String title;

    /**
     * The description of the event.
     */
    @Column(name = "description")
    private String description;

    /**
     * Host user of the event.
     * One event has one host.
     */
    @OneToOne
    private User host;

    /**
     * Users attending to the event.
     * One event has many users attending.
     */
    @OneToMany
    private Collection<InvitedUser> invitedUsers;

    /**
     * When is the event.
     */
    @Column(name = "when")
    private Date when;

    /**
     * Where is the event.
     */
    @Column(name = "where")
    private String where;

    public Event() {
    }
}
