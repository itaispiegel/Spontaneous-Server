package com.spontaneous.server.model.entity;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * This class represents an event persisted in the database.
 */
@Entity
@Table(name = "events")
public class Event extends BaseEntity {

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
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime birthday;

    /**
     * Where is the event.
     */
    @Column(name = "where")
    private String where;
}
