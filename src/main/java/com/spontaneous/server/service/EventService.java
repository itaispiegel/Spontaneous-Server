package com.spontaneous.server.service;

import com.spontaneous.server.model.entity.Event;
import com.spontaneous.server.model.entity.InvitedUser;
import com.spontaneous.server.model.entity.User;
import com.spontaneous.server.model.request.CreateEventRequest;
import com.spontaneous.server.repository.EventRepository;
import com.spontaneous.server.repository.InvitedUserRepository;
import com.spontaneous.server.repository.UserRepository;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is part of the service layer of the application and is used for user manipulation in the database.
 */
@Service
public class EventService {

    private final Logger mLogger;

    private final EventRepository mEventRepository;
    private final UserRepository mUserRepository;
    private final InvitedUserRepository mInvitedUserRepository;

    @Autowired
    public EventService(EventRepository eventRepository, UserRepository userRepository, InvitedUserRepository invitedUserRepository) {

        mLogger = LoggerFactory.getLogger(this.getClass());

        mEventRepository = eventRepository;
        mUserRepository = userRepository;
        mInvitedUserRepository = invitedUserRepository;
    }

    /**
     * Store event in database.
     *
     * @param createEventRequest The request of the event to create.
     * @return The stored event.
     */
    public Event createEvent(CreateEventRequest createEventRequest) {

        //Build the event from the request object.
        Event event = new Event.Builder()
                .title(createEventRequest.getTitle())
                .description(createEventRequest.getDescription())
                .date(createEventRequest.getDate())
                .location(createEventRequest.getLocation())
                .host(mUserRepository.findOne(createEventRequest.getHostUserId()))
                .build();

        //Save the event, then invite the users to it.
        event = mEventRepository.save(event);

        return inviteUsers(createEventRequest.getInvitedUsersEmails(), event);
    }

    /**
     * Invite list of users to an event.
     *
     * @param emails Of users to invite.
     * @param event  To invite them to.
     */
    public Event inviteUsers(List<String> emails, Event event) {

        mLogger.info("Inviting the following users: {}", emails);

        //The size allocated is for the users in the given list of emails, and for the host.
        ArrayList<InvitedUser> invitedUsers = new ArrayList<>(emails.size() + 1);

        //Invite the host to the invited users list.
        invitedUsers.add(mInvitedUserRepository.save(
                new InvitedUser(event.getHost(), event)
        ));

        //Loop over each email in the given collection, and invite each user.
        for (String email : emails) {
            //TODO: Send GCM push notification

            User user = mUserRepository.findByEmail(email);

            //Only invite the user if he is using spontaneous
            if (user != null && !user.equals(event.getHost())) {
                invitedUsers.add(mInvitedUserRepository.save(
                        new InvitedUser(user, event)
                ));
            }
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
