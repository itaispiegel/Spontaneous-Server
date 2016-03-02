package com.spontaneous.server.service;

import com.spontaneous.server.model.entity.Event;
import com.spontaneous.server.model.entity.InvitedUser;
import com.spontaneous.server.model.request.SaveEventRequest;
import com.spontaneous.server.model.request.UpdateInvitedUserRequest;
import com.spontaneous.server.repository.EventRepository;
import com.spontaneous.server.repository.InvitedUserRepository;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * This class is part of the service layer of the application and is used for user manipulation in the database.
 */
@Service
public class EventService {

    private final Logger mLogger;

    private final EventRepository mEventRepository;
    private final UserService mUserService;
    private final InvitedUserRepository mInvitedUserRepository;
    private final GcmService mGcmService;

    @Autowired
    public EventService(EventRepository eventRepository, UserService userService, InvitedUserRepository invitedUserRepository, GcmService gcmService) {

        mLogger = LoggerFactory.getLogger(this.getClass());

        mEventRepository = eventRepository;
        mUserService = userService;
        mInvitedUserRepository = invitedUserRepository;
        mGcmService = gcmService;
    }

    /**
     * Store event in database.
     *
     * @param saveEventRequest The request of the event to create.
     * @return The stored event.
     */
    public Event createEvent(SaveEventRequest saveEventRequest) throws ServiceException, IOException {

        //ServiceException is caught in case that the host user does not exist.
        //IOException is caught in case that the GCMService was unable to send a notification.

        //Build the event from the request object.
        Event event = new Event.Builder()
                .title(saveEventRequest.getTitle())
                .description(saveEventRequest.getDescription())
                .date(saveEventRequest.getDate())
                .location(saveEventRequest.getLocation())
                .host(mUserService.getUserById(saveEventRequest.getHostUserId()))
                .build();

        //Add the invited users to the event.
        event = addInvitedUsers(saveEventRequest.getInvitedUsersEmails(), event);

        //Save the event in the database.
        event = mEventRepository.save(event);

        //Notify the invitedUsers.
        for (InvitedUser invitedUser : event.getInvitedUsers()) {
            mGcmService.sendInvitation(invitedUser);
        }

        return event;
    }


    /**
     * Update an existing event in the database.
     *
     * @param saveEventRequest The request of the event to create.
     * @param id               The id of the event we wish to edit.
     * @return The stored event.
     */
    public Event updateEvent(long id, SaveEventRequest saveEventRequest) throws ServiceException {
        Event event = mEventRepository.findOne(id);

        //In case no such event is found, throw an exception.
        if (event == null) {
            throw new ServiceException(String.format("There is no such event with id #%d", id));
        }

        event.setTitle(saveEventRequest.getTitle());
        event.setDescription(saveEventRequest.getDescription());
        event.setDate(saveEventRequest.getDate());
        event.setLocation(saveEventRequest.getLocation());
        event.setHost(mUserService.getUserById(saveEventRequest.getHostUserId()));

        //Clear the invited users list, and add the invited users to the event.
        event.clearInvitedUsers();
        event = addInvitedUsers(saveEventRequest.getInvitedUsersEmails(), event);

        //Save the event in the database.
        return mEventRepository.save(event);
    }


    /**
     * Invite a list of users to an event.
     * If a user in the given {@link HashSet} of emails isn't using Spontaneous, then print a relevant message to the logger.
     * The method also sends a push notification to each user invited.
     *
     * @param emails Of users to invite.
     * @param event  To invite them to.
     */
    private Event addInvitedUsers(HashSet<String> emails, Event event) {

        //In case no users are invited, invite only the host user.
        if (emails == null) {
            emails = new HashSet<>(1);
        }

        //Add the host to the set of emails.
        emails.add(event.getHost().getEmail());

        mLogger.info("Inviting the following users: {}", emails);

        //The size allocated is for the users in the given set of emails.
        ArrayList<InvitedUser> invitedUsers = new ArrayList<>(emails.size());

        //Loop over each email in the given collection, and invite each user.
        for (String email : emails) {

            try {

                InvitedUser invitedUser = new InvitedUser(mUserService.getUserByEmail(email), event);
                invitedUsers.add(invitedUser);

            } catch (ServiceException e) {
                //The ServiceException is caught in case that there is no user with the given email.
                //In this case, print the exception and don't invite the user.
                mLogger.error(e.getMessage());
            }
        }

        //Set the invited users list to the event entity.
        event.inviteUsers(invitedUsers);

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
        if (!mUserService.exists(userId)) {
            throw new ServiceException(String.format("No such user with id #%d.", userId));
        }

        return mEventRepository.findByInvitedUser(userId);
    }

    /**
     * Update an {@link InvitedUser} according to the given {@link UpdateInvitedUserRequest}.
     *
     * @param id            Id of the {@link InvitedUser} we wish to update.
     * @param updateRequest The update request.
     * @return The updated {@link InvitedUser} entity.
     * @throws ServiceException In case that there is no such {@link InvitedUser} with the given id.
     */
    public InvitedUser updateInvitedUser(long id, UpdateInvitedUserRequest updateRequest) throws ServiceException {

        //Throw an exception if there is no user with the given id.
        InvitedUser invitedUser = mInvitedUserRepository.findOne(id);

        if (invitedUser == null) {
            throw new ServiceException(String.format("There is no InvitedUser with the id #%s.", id));
        }

        //Update the InvitedUser fields.
        invitedUser.update(updateRequest);

        return mInvitedUserRepository.save(invitedUser);
    }

    /**
     * Delete an event by it's id.
     *
     * @param id Id of the event.
     * @return The deleted event details.
     * @throws ServiceException In case there is no such event.
     */
    public Event deleteEvent(long id) throws ServiceException {
        Event deletedEvent = mEventRepository.findOne(id);

        if (deletedEvent == null) {
            throw new ServiceException(String.format("No such event with id #%d.", id));
        }

        mEventRepository.delete(id);

        return deletedEvent;
    }

    /**
     * A service method for sending a broadcast message to all users of an event.
     *
     * @param id      The id of the event.
     * @param message The message to broadcast.
     */
    public void notifyGuests(long id, String message) {
        Event event = mEventRepository.findOne(id);

        for (InvitedUser invitedUser : event.getInvitedUsers()) {

            //Don't notify the host of the event.
            if (invitedUser.getUser().equals(event.getHost())) {
                continue;
            }

            try {

                //Try to broadcast the message to each guest.
                mGcmService.sendBroadcastMessage(invitedUser, message);
            } catch (IOException e) {
                mLogger.error(e.getMessage());
            }
        }
    }
}
