package com.spontaneous.server.model.entity;

import com.spontaneous.server.model.entity.representational.EventRO;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
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
    private User host;

    /**
     * Users attending the event.
     * One event has many users attending.
     */
    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Guest> guests;

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
     * When the event is.
     */
    @Column(name = "date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime date;

    /**
     * Where the event is.
     */
    @Column(name = "location")
    private String location;

    public Event() {
    }

    public EventRO createRepresentationalObject() {
        return new EventRO(getId(), host, title, description, date, location, guests);
    }

    private Event(Builder builder) {
        setHost(builder.host);
        inviteUsers(builder.guests);
        setTitle(builder.title);
        setDescription(builder.description);
        setDate(builder.date);
        setLocation(builder.location);
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    /**
     * Invite a list of users to the event.
     *
     * @param guests The list of users to invite.
     */
    public void inviteUsers(List<Guest> guests) {

        //Initialize the invited users list field if it is null.
        if (this.guests == null) {
            this.guests = new ArrayList<>();
        }

        //Return void if the given list is null.
        if (guests == null) {
            return;
        }

        this.guests.addAll(guests);
    }

    public void clearGuests() {
        guests.clear();
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


    /**
     * {@code Event} builder static inner class.
     */
    public static final class Builder {
        private User host;
        private List<Guest> guests;
        private String title;
        private String description;
        private DateTime date;
        private String location;

        public Builder() {
        }

        /**
         * Sets the {@code host} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code host} to set
         * @return a reference to this Builder
         */
        public Builder host(User val) {
            host = val;
            return this;
        }

        /**
         * Sets the {@code guests} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code guests} to set
         * @return a reference to this Builder
         */
        public Builder guests(List<Guest> val) {
            guests = val;
            return this;
        }

        /**
         * Sets the {@code title} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code title} to set
         * @return a reference to this Builder
         */
        public Builder title(String val) {
            title = val;
            return this;
        }

        /**
         * Sets the {@code description} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code description} to set
         * @return a reference to this Builder
         */
        public Builder description(String val) {
            description = val;
            return this;
        }

        /**
         * Sets the {@code date} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code date} to set
         * @return a reference to this Builder
         */
        public Builder date(DateTime val) {
            date = val;
            return this;
        }

        /**
         * Sets the {@code location} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code location} to set
         * @return a reference to this Builder
         */
        public Builder location(String val) {
            location = val;
            return this;
        }

        /**
         * Returns a {@code Event} built from the parameters previously set.
         *
         * @return a {@code Event} built with parameters of this {@code Event.Builder}
         */
        public Event build() {
            return new Event(this);
        }
    }
}
