package com.spontaneous.server.service;

import com.spontaneous.server.model.entity.Event;
import com.spontaneous.server.model.entity.User;
import com.spontaneous.server.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is part of the service layer of the application and is used for user manipulation in the database.
 */
@Service
public class EventService {

    @Autowired
    private EventRepository mEventRepository;

    /**
     * Store event in database.
     * @param event to store.
     * @return The stored event.
     */
    public Event createEvent(Event event) {
        return mEventRepository.save(event);
    }

    /**
     * @return Events relating to users (hosting/invited to).
     */
    public List<Event> getUserEvents(User user) {
        ArrayList<Event> events = new ArrayList<>();

        return events;
    }

}
