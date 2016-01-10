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

import java.util.List;

/**
 * This is a REST controller for event operations.
 */
@RestController
@RequestMapping(value = "/API/events")
public class EventController {

    private final Logger mLogger;

    private EventService mEventService;
    private UserService mUserService;

    @Autowired
    public EventController(EventService eventService, UserService userService) {
        mLogger =  LoggerFactory.getLogger(this.getClass());
        mEventService = eventService;
        mUserService = userService;
    }

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

    @RequestMapping(value = "/getUserEvents", method = RequestMethod.GET)
    public BaseResponse getUserEvents(@RequestParam("user_id") long id) {

        try {

            User user = mUserService.getUserById(id);
            mLogger.info("Getting user events for user {}", user);
            List<Event> events = mEventService.getUserEvents(user);

            return new BaseResponse<>(events);
        } catch (ServiceException e) {
            return new BaseResponse<>(e.getMessage());
        }
    }
}
