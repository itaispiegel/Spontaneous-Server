package com.spontaneous.server.service;

import com.spontaneous.server.model.entity.Event;
import com.spontaneous.server.model.entity.Guest;
import com.spontaneous.server.model.entity.Item;
import com.spontaneous.server.model.request.SaveEventRequest;
import com.spontaneous.server.model.request.UpdateGuestRequest;
import com.spontaneous.server.repository.EventRepository;
import com.spontaneous.server.repository.GuestRepository;
import com.spontaneous.server.repository.ItemRepository;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private final GuestRepository mGuestRepository;
    private final ItemRepository mItemRepository;

    private final GcmService mGcmService;

    @Autowired
    public EventService(EventRepository eventRepository, UserService userService, GuestRepository guestRepository,
                        ItemRepository itemRepository, GcmService gcmService) {

        mLogger = LoggerFactory.getLogger(this.getClass());

        mEventRepository = eventRepository;
        mUserService = userService;
        mGuestRepository = guestRepository;
        mItemRepository = itemRepository;

        mGcmService = gcmService;
    }

    /**
     * Store event in database.
     *
     * @param saveEventRequest The request of the event to create.
     * @return The stored event.
     */
    public Event createEvent(SaveEventRequest saveEventRequest) throws ServiceException {

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

        //Add the guests to the event.
        event = addGuests(saveEventRequest.getGuestsEmails(), event);

        //Save the event in the database.
        event = mEventRepository.save(event);

        //Notify the guests.
        event.getGuests().forEach(mGcmService::sendInvitation);

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

        //Clear the guests list, and add the guests to the event.
        event.clearGuests();
        event = addGuests(saveEventRequest.getGuestsEmails(), event);

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
    private Event addGuests(HashSet<String> emails, Event event) {

        //In case no users are invited, invite only the host user.
        if (emails == null) {
            emails = new HashSet<>(1);
        }

        //Add the host to the set of emails.
        emails.add(event.getHost().getEmail());

        mLogger.info("Inviting the following users: {}", emails);

        //The size allocated is for the users in the given set of emails.
        ArrayList<Guest> guests = new ArrayList<>(emails.size());

        //Loop over each email in the given collection, and invite each user.
        for (String email : emails) {

            try {

                Guest guest = new Guest(mUserService.getUserByEmail(email), event);
                guests.add(guest);

            } catch (ServiceException e) {
                //The ServiceException is caught in case that there is no user with the given email.
                //In this case, print the exception and don't invite the user.
                mLogger.error(e.getMessage());
            }
        }

        //Set the invited users list to the event entity.
        event.inviteUsers(guests);

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

        return mEventRepository.findByGuest(userId);
    }

    /**
     * Update an {@link Guest} according to the given {@link UpdateGuestRequest}.
     *
     * @param id            Id of the {@link Guest} we wish to update.
     * @param updateRequest The update request.
     * @return The updated {@link Guest} entity.
     * @throws ServiceException In case that there is no such {@link Guest} with the given id.
     */
    public Guest updateGuest(long id, UpdateGuestRequest updateRequest) throws ServiceException {

        //Throw an exception if there is no user with the given id.
        Guest guest = mGuestRepository.findOne(id);

        if (guest == null) {
            throw new ServiceException(String.format("There is no Guest with the id #%s.", id));
        }

        //Update the Guest fields.
        guest.update(updateRequest);

        return mGuestRepository.save(guest);
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

        for (Guest guest : event.getGuests()) {

            //Don't notify the host of the event.
            if (guest.getUser().equals(event.getHost())) {
                continue;
            }

            mGcmService.sendBroadcastMessage(guest, message);
        }
    }

    /**
     * A service method for assigning an item to a guest.
     *
     * @param id    Id of the guest.
     * @param title Title of the item.
     */
    public Guest assignItem(long id, String title) throws ServiceException {
        Guest guest = mGuestRepository.getOne(id);

        if (guest == null) {
            throw new ServiceException(String.format("No such guest with id #%d", id));
        }

        Item newItem = new Item(guest, guest.getEvent(), title, false);
        guest.addItem(newItem);

        //Save the new item and notify the guest.
        newItem = mItemRepository.save(newItem);
        mGcmService.assignItem(newItem);

        //Return the guest entity.
        return mGuestRepository.findOne(guest.getId());
    }

    /**
     * Delete an item with a given id.
     *
     * @param id Given id of the item to delete.
     * @return The deleted item.
     * @throws ServiceException In case that there is no such item.
     */
    public Item deleteItem(long id) throws ServiceException {
        Item item = mItemRepository.findOne(id);

        if (item == null) {
            throw new ServiceException(String.format("No such item with id #%d", id));
        }

        mItemRepository.delete(id);
        return item;
    }

    /**
     * Delete a guest with a given id.
     *
     * @param id Id of the guest to delete.
     * @return The deleted guest.
     * @throws ServiceException In case that there is no such guest.
     */
    public Guest deleteGuest(long id) throws ServiceException {
        Guest guest = mGuestRepository.findOne(id);

        if (guest == null) {
            throw new ServiceException(String.format("No such guest with id #%d", id));
        }

        mGuestRepository.delete(id);
        return guest;
    }

    public Item updateItem(long id, boolean isBringing) throws ServiceException {
        Item item = mItemRepository.findOne(id);

        if (item == null) {
            throw new ServiceException(String.format("No such item with id #%d", id));
        }

        item.setBringing(isBringing);
        return mItemRepository.save(item);
    }
}
