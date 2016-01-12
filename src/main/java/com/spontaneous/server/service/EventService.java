package com.spontaneous.server.service;

import com.spontaneous.server.model.entity.Event;
import com.spontaneous.server.model.entity.InvitedUser;
import com.spontaneous.server.model.request.CreateEventRequest;
import com.spontaneous.server.repository.EventRepository;
import com.spontaneous.server.repository.UserRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is part of the service layer of the application and is used for user manipulation in the database.
 */
@Service
public class EventService {

    private final EventRepository mEventRepository;
    private final UserRepository mUserRepository;

    @Autowired
    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        mEventRepository = eventRepository;
        mUserRepository = userRepository;
    }

    /**
     * Store event in database.
     *
     * @param createEventRequest The request of the event to create.
     * @return The stored event.
     */
    public Event createEvent(CreateEventRequest createEventRequest) {

        Event event = new Event.Builder()
                .title(createEventRequest.getTitle())
                .description(createEventRequest.getDescription())
                .date(createEventRequest.getDate())
                .location(createEventRequest.getLocation())
                .host(mUserRepository.findOne(createEventRequest.getHostUserId()))
                .build();

        event = inviteUsers(createEventRequest.getInvitedUsers(), event);

        return mEventRepository.save(event);
    }

    /**
     * Invite list of users to an event.
     *
     * @param emails Of users to invite.
     * @param event  To invite them to.
     */
    public Event inviteUsers(List<String> emails, Event event) {

        //The size allocated is for the users in the given list of emails, and for the host.
        ArrayList<InvitedUser> invitedUsers = new ArrayList<>(emails.size() + 1);

        //Invite the host to the invited users list.
        invitedUsers.add(new InvitedUser(
                event.getHost(), event, "", false
        ));

        //Loop over each email in the given collection, and invite each user.
        for (String email : emails) {
            //TODO: Send GCM push notification

            InvitedUser invitedUser = new InvitedUser(mUserRepository.findByEmail(email), event, "", false);
            invitedUsers.add(invitedUser);
        }

        //Set the invited users list to the event entity.
        event.setInvitedUsers(invitedUsers);
        return event;
    }

    /**
     * Get events relating to the given user (hosting/invited to).
     *
     * @param userId Id of the user to get events for.
     * @return List of events.
     * @throws ServiceException In case there is no such user with the given id.
     */
    public List<Event> getUserEvents(long userId) throws ServiceException {

        //In case there is no such user, throw an exception.
        if (!mUserRepository.exists(userId)) {
            throw new ServiceException(String.format("No such user with id #%d.", userId));
        }

        return mEventRepository.findByInvitedUser(userId);
    }
}
