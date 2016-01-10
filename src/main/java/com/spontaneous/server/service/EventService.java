package com.spontaneous.server.service;

import com.spontaneous.server.model.entity.Event;
import com.spontaneous.server.model.entity.User;
import com.spontaneous.server.repository.EventRepository;
import org.apache.log4j.Logger;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class is part of the service layer of the application and is used for user manipulation in the database.
 */
@Service
public class EventService {

    private final Logger mLogger;

    private final EventRepository mEventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        mLogger = Logger.getLogger(this.getClass());
        mEventRepository = eventRepository;
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
     * @return Events relating to given user (hosting/invited to).
     */
    public List<Event> getUserEvents(User user) throws ServiceException {

        try {
            return mEventRepository.findByInvitedUser(user.getId());
        } catch(ServiceException e) {
            mLogger.error(e.getMessage());
            throw e;
        }
    }

}
