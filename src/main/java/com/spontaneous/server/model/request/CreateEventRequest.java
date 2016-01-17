package com.spontaneous.server.model.request;

import com.google.gson.annotations.Expose;
import org.joda.time.DateTime;

import java.util.HashSet;

/**
 * This class represents an HTTP POST request to create a new event.
 */
public class CreateEventRequest {

    /**
     * The title of the event.
     */
    @Expose
    private final String title;

    /**
     * A short description about the event.
     */
    @Expose
    private final String description;

    /**
     * Id number of the host user.
     * NOTICE: The request entity only holds the id - there is no need to hold the full user details.
     */
    @Expose
    private final long hostUserId;

    /**
     * Emails of the users to invite.
     * NOTICE: The request entity only holds the emails - there is no need to hold the full invited users details (memory cost).
     * A {@link HashSet} is good in this case, since we want the emails to be unique.
     */
    @Expose
    private final HashSet<String> invitedUsersEmails;

    /**
     * When the event is.
     */
    @Expose
    private final DateTime date;

    /**
     * Where the event is.
     */
    @Expose
    private final String location;

    public CreateEventRequest(String title, String description, long hostUserId, HashSet<String> invitedUsersEmails, DateTime date, String location) {
        this.title = title;
        this.description = description;
        this.hostUserId = hostUserId;
        this.invitedUsersEmails = invitedUsersEmails;
        this.date = date;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getHostUserId() {
        return hostUserId;
    }

    public HashSet<String> getInvitedUsersEmails() {
        return invitedUsersEmails;
    }

    public DateTime getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "CreateEventRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", hostUserId=" + hostUserId +
                ", invitedUsersEmails=" + invitedUsersEmails +
                ", date=" + date +
                ", location='" + location + '\'' +
                '}';
    }
}
