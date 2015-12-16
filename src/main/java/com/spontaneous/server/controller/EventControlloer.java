package com.spontaneous.server.controller;

import com.spontaneous.server.config.BaseComponent;
import com.spontaneous.server.model.entity.Event;
import com.spontaneous.server.model.response.BaseResponse;
import com.spontaneous.server.service.EventService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is a REST controller for event operations.
 */
@RestController
@RequestMapping(value = "/API/events")
public class EventControlloer extends BaseComponent {

    @Autowired
    private EventService mEventService;


    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse createEvent(@RequestBody Event event) {

        try {

            mLogger.info("Create Event Request {}", event);
            event = mEventService.createEvent(event);

            return new BaseResponse<>(BaseResponse.SUCCESS, event);

        } catch (ServiceException e) {
            return new BaseResponse<>(BaseResponse.INTERNAL_ERROR, e.getMessage());
        }

    }
}
