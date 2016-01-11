package com.spontaneous.server.service;

import com.spontaneous.server.model.entity.Event;
import com.spontaneous.server.repository.EventRepository;
import com.spontaneous.server.repository.UserRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * @param event to store.
     * @return The stored event.
     */
    public Event createEvent(Event event) {
        return mEventRepository.save(event);
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
