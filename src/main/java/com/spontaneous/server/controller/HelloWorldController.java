package com.spontaneous.server.controller;

import com.spontaneous.server.model.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Just a controller for testing and playing around.
 */
@RestController
@RequestMapping(value = "/API/hello")
public class HelloWorldController {

    private Logger mLogger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public BaseResponse<String> greet(@PathVariable String name) {
        BaseResponse<String> response = new BaseResponse<>("Hello, " + name + "!");

        mLogger.info("Received request from {}", name);

        return response;
    }
}
