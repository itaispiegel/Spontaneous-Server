package com.spontaneous.server.handler;

import com.spontaneous.server.model.entity.Event;
import com.spontaneous.server.model.entity.representational.EventRO;
import com.spontaneous.server.model.request.SaveEventRequest;
import com.spontaneous.server.model.response.BaseResponse;
import com.spontaneous.server.service.EventService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The handler layer is between the controller layer, and the service layer, and it's purpose is to translate data objects to representational object.
 */
@Service
public class EventHandler {

    private final EventService mEventService;

    @Autowired
    public EventHandler(EventService mEventService) {
        this.mEventService = mEventService;
    }

    /**
     * Create a new event, given the saveEventRequest details.
     *
     * @param saveEventRequest The details of the event.
     * @return {@link BaseResponse} stating the result of the process.
     */
    public EventRO createEvent(SaveEventRequest saveEventRequest) throws IOException, ServiceException {
        return mEventService.createEvent(saveEventRequest).createRepresentationalObject();
    }

    /**
     * Update an existing event, given the saveEventRequest details.
     *
     * @param saveEventRequest The details of the event.
     * @return {@link BaseResponse} stating the result of the process.
     */
    public EventRO updateEvent(long id, SaveEventRequest saveEventRequest) throws ServiceException {
        return mEventService.updateEvent(id, saveEventRequest).createRepresentationalObject();
    }

    /**
     * Get list of events relating to the given user.
     *
     * @param id Id of the given user.
     * @return The list of events.
     * @throws ServiceException
     */
    public List<EventRO> getUserEvents(long id) throws ServiceException {
        return mEventService.getUserEvents(id)
                .stream()
                .map(Event::createRepresentationalObject)
                .collect(Collectors.toList());
    }
}
