package com.spontaneous.server.controller;

import com.spontaneous.server.model.entity.Event;
import com.spontaneous.server.model.entity.Guest;
import com.spontaneous.server.model.entity.representational.EventRO;
import com.spontaneous.server.model.entity.representational.GuestRO;
import com.spontaneous.server.model.request.SaveEventRequest;
import com.spontaneous.server.model.request.UpdateGuestRequest;
import com.spontaneous.server.model.response.BaseResponse;
import com.spontaneous.server.service.EventService;
import com.spontaneous.server.util.Converter;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This is a REST controller for saveEventRequest operations.
 */
@RestController
@RequestMapping(value = "/API/events")
public class EventController {

    private final Logger mLogger;
    private final EventService mEventService;

    @Autowired
    public EventController(EventService eventService) {
        mLogger = LoggerFactory.getLogger(this.getClass());
        mEventService = eventService;
    }

    /**
     * A controller method for creating a new event, given the saveEventRequest details.
     * In case of {@link ServiceException}, return the error.
     *
     * @param saveEventRequest The details of the event - given in JSON.
     * @return {@link BaseResponse} stating the result of the process.
     */
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse createEvent(@RequestBody SaveEventRequest saveEventRequest) {

        try {

            mLogger.info("Create Event Request {}", saveEventRequest);

            EventRO createdEvent = mEventService.createEvent(saveEventRequest)
                    .createRepresentationalObject();

            return new BaseResponse<>(createdEvent);

        } catch (ServiceException e) {
            mLogger.error(e.getMessage());
            return new BaseResponse<>(e.getMessage(), BaseResponse.INTERNAL_ERROR);
        }
    }

    /**
     * A controller method for updating an existing event, given the saveEventRequest details.
     * In case of {@link ServiceException}, return the error.
     *
     * @param saveEventRequest The details of the event - given in JSON.
     * @return {@link BaseResponse} stating the result of the process.
     */
    @RequestMapping(method = RequestMethod.PUT)
    public BaseResponse updateEvent(@RequestParam("id") long id, @RequestBody SaveEventRequest saveEventRequest) {

        try {

            mLogger.info("Update Event Request, for event with id #" + id + ": " + saveEventRequest);

            EventRO updatedEvent = mEventService.updateEvent(id, saveEventRequest)
                    .createRepresentationalObject();

            return new BaseResponse<>(updatedEvent);

        } catch (ServiceException e) {
            mLogger.error(e.getMessage());
            return new BaseResponse<>(e.getMessage(), BaseResponse.INTERNAL_ERROR);
        }
    }

    /**
     * A controller method for requesting the user events, given the user id.
     *
     * @param id Of the user to get events for.
     * @return {@link BaseResponse} stating the result of the process.
     */
    @RequestMapping(value = "/getUserEvents", method = RequestMethod.GET)
    public BaseResponse getUserEvents(@RequestParam("id") long id) {

        try {

            mLogger.info("Getting user events for user with id #{}", id);

            List<EventRO> events = Converter.convertList(mEventService.getUserEvents(id), Event::createRepresentationalObject);

            return new BaseResponse<>(events);

        } catch (ServiceException e) {
            mLogger.error(e.getMessage());
            return new BaseResponse<>(e.getMessage(), BaseResponse.INTERNAL_ERROR);
        }
    }

    /**
     * A controller method for updating an {@link Guest}.
     *
     * @param id            The id of the invited user, we wish to update.
     * @param updateRequest The new details of the invited user.
     * @return {@link BaseResponse} representing the updated invited user, or the error occurred.
     */
    @RequestMapping(value = "/updateGuest", method = RequestMethod.PUT)
    public BaseResponse updateGuest(@RequestParam("id") long id, @RequestBody UpdateGuestRequest updateRequest) {

        try {
            mLogger.info("Updating guest with id #{}", id);

            GuestRO guest = mEventService.updateGuest(id, updateRequest)
                    .createRepresentationalObject();

            return new BaseResponse<>(guest);
        } catch (ServiceException e) {
            mLogger.error(e.getMessage());
            return new BaseResponse<>(e.getMessage(), BaseResponse.INTERNAL_ERROR);
        }
    }

    /**
     * A controller method for deleting an event.
     *
     * @param id Id of the event we wish to delete.
     * @return {@link BaseResponse} representing the result of the action.
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public BaseResponse deleteEvent(@RequestParam("id") long id) {

        try {

            mLogger.info("Deleting event with id #{}", id);

            EventRO deletedEvent = mEventService.deleteEvent(id)
                    .createRepresentationalObject();

            return new BaseResponse<>(deletedEvent);
        } catch (ServiceException e) {
            mLogger.error(e.getMessage());
            return new BaseResponse<>(e.getMessage(), BaseResponse.INTERNAL_ERROR);
        }

    }

    /**
     * A controller method for sending a broadcast message to all users invited to a specific event.
     *
     * @param id      Id of the event to broadcast the message to.
     * @param message The content of the message to broadcast.
     */
    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    public void notifyGuests(@RequestParam("id") long id, @RequestBody String message) {
        mLogger.info("Broadcasting message to guests of event #{}", id);
        mEventService.notifyGuests(id, message);
    }

    @RequestMapping(value = "/assign", method = RequestMethod.GET)
    public BaseResponse assignItem(@RequestParam("id") long id, @RequestParam("title") String title) {
        mLogger.info("Assigning item '{}' for guest with id #{}", title, id);

        try {
            Guest guest = mEventService.assignItem(id, title);
            return new BaseResponse<>(guest.createRepresentationalObject());
        } catch (ServiceException e) {
            mLogger.error(e.getMessage());
            return new BaseResponse<>(e.getMessage(), BaseResponse.INTERNAL_ERROR);
        }
    }
}