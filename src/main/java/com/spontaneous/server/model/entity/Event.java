package com.spontaneous.server.model.entity;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.rest.core.config.Projection;

import javax.persistence.*;
import java.util.List;

/**
 * This class represents an event persisted in the database.
 */
@Entity
@Table(name = "events")
public class Event extends BaseEntity {

    /**
     * Host user of the event.
     * Many events have one host.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "host_user_id")
    @Expose
    private User host;

    /**
     * Users attending to the event.
     * One event has many users attending.
     */
    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER)
    @Expose
    private List<InvitedUser> invitedUsers;

    /**
     * The title of the event.
     */
    @Column(name = "title")
    @Expose
    private String title;

    /**
     * The description of the event.
     */
    @Column(name = "description")
    @Expose
    private String description;

    /**
     * When the event is.
     */
    @Column(name = "date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Expose
    private DateTime date;

    /**
     * Where the event is.
     */
    @Column(name = "location")
    @Expose
    private String location;

    public Event() {
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public List<InvitedUser> getInvitedUsers() {
        return invitedUsers;
    }

    public void setInvitedUsers(List<InvitedUser> invitedUsers) {
        this.invitedUsers = invitedUsers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
