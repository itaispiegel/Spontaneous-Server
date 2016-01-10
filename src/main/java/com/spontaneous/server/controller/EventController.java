package com.spontaneous.server.controller;

import com.spontaneous.server.model.entity.Event;
import com.spontaneous.server.model.entity.User;
import com.spontaneous.server.model.response.BaseResponse;
import com.spontaneous.server.service.EventService;
import com.spontaneous.server.service.UserService;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * This is a REST controller for event operations.
 */
@RestController
@RequestMapping(value = "/API/events")
public class EventController {

    private final Logger mLogger;

    private final EventService mEventService;
    private final UserService mUserService;

    @Autowired
    public EventController(EventService eventService, UserService userService) {
        mLogger = LoggerFactory.getLogger(this.getClass());
        mEventService = eventService;
        mUserService = userService;
    }

    /**
     * A controller method for creating a new event, given the event details.
     * In case of {@link ServiceException} return the error.
     *
     * @param event The details of the event - given in JSON.
     * @return {@link BaseResponse} stating the result of the process.
     */
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse createEvent(@RequestBody Event event) {

        try {

            mLogger.info("Create Event Request {}", event);
            event = mEventService.createEvent(event);

            return new BaseResponse<>(event);

        } catch (ServiceException e) {
            return new BaseResponse<>(e.getMessage());
        }
    }

    /**
     * A controller method for requesting the user events, given the user id.
     *
     * @param id Of the user.
     * @return {@link BaseResponse} stating the result of the process.
     */
    @RequestMapping(value = "/getUserEvents", method = RequestMethod.GET)
    public BaseResponse getUserEvents(@RequestParam("user_id") long id) {

        try {

            User user = mUserService.getUserById(id);
            mLogger.info("Getting user events for {}", user.getName());
            return new BaseResponse<>(mEventService.getUserEvents(user));

        } catch (NullPointerException | ServiceException e) {
            return new BaseResponse<>(BaseResponse.INTERNAL_ERROR, e.getMessage());
        }
    }
}
