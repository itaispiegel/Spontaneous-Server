package com.spontaneous.server.controller;

import com.spontaneous.server.model.entity.Event;
import com.spontaneous.server.model.entity.InvitedUser;
import com.spontaneous.server.model.request.SaveEventRequest;
import com.spontaneous.server.model.request.UpdateInvitedUserRequest;
import com.spontaneous.server.model.response.BaseResponse;
import com.spontaneous.server.service.EventService;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
     * A controller method for creating a new saveEventRequest, given the saveEventRequest details.
     * In case of {@link ServiceException}, return the error.
     *
     * @param saveEventRequest The details of the saveEventRequest - given in JSON.
     * @return {@link BaseResponse} stating the result of the process.
     */
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse createEvent(@RequestBody SaveEventRequest saveEventRequest) {

        try {

            mLogger.info("Create Event Request {}", saveEventRequest);
            Event event = mEventService.updateEvent(saveEventRequest);

            return new BaseResponse<>(event);

        } catch (IOException | ServiceException e) {
            return new BaseResponse<>(e.getMessage(), BaseResponse.INTERNAL_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public BaseResponse updateEvent(@RequestParam("id") long id, @RequestBody SaveEventRequest saveEventRequest) {

        try {

            mLogger.info("Update Event Request, for event with id #" + id + ": " + saveEventRequest);
            Event event = mEventService.updateEvent(saveEventRequest);
            return new BaseResponse<>(event);

        } catch (ServiceException | IOException e) {
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
            return new BaseResponse<>(mEventService.getUserEvents(id));

        } catch (ServiceException e) {
            return new BaseResponse<>(e.getMessage(), BaseResponse.INTERNAL_ERROR);
        }
    }

    /**
     * A controller method for updating an {@link InvitedUser}.
     *
     * @param id            The id of the invited user, we wish to update.
     * @param updateRequest The new details of the invited user.
     * @return {@link BaseResponse} representing the updated invited user, or the error occurred.
     */
    @RequestMapping(value = "/updateInvitedUser", method = RequestMethod.PUT)
    public BaseResponse updateInvitedUser(@RequestParam("id") long id, @RequestBody UpdateInvitedUserRequest updateRequest) {

        try {
            mLogger.info("Updating invited user with id #{}", id);

            InvitedUser invitedUser = mEventService.updateInvitedUser(id, updateRequest);
            return new BaseResponse<>(invitedUser);
        } catch (ServiceException e) {
            return new BaseResponse<>(e.getMessage(), BaseResponse.INTERNAL_ERROR);
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public BaseResponse deleteEvent(@RequestParam("id") long id) {

        try {

            mLogger.info("Deleting event with id #{}", id);

            Event deletedEvent = mEventService.deleteEvent(id);
            return new BaseResponse<>(deletedEvent);

        } catch (ServiceException e) {
            return new BaseResponse<>(e.getMessage(), BaseResponse.INTERNAL_ERROR);
        }

    }
}
