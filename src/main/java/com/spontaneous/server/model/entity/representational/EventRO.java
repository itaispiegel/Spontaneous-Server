package com.spontaneous.server.model.entity.representational;

import com.spontaneous.server.model.entity.Event;
import org.joda.time.DateTime;

import java.util.List;

/**
 * This is a representational object for the {@link Event} entity.
 */
public class EventRO {

    private final UserProfileRO host;
    private final List<InvitedUserRO> invitedUsers;
    private final String title;
    private final String description;
    private final DateTime date;
    private final String location;

    public EventRO(UserProfileRO host, List<InvitedUserRO> invitedUsers, String title, String description, DateTime date, String location) {
        this.host = host;
        this.invitedUsers = invitedUsers;
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
    }

    public UserProfileRO getHost() {
        return host;
    }

    public List<InvitedUserRO> getInvitedUsers() {
        return invitedUsers;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public DateTime getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }
}
