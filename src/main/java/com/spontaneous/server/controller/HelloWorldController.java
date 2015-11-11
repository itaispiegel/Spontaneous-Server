package com.spontaneous.server.controller;

import com.spontaneous.server.config.BaseComponent;
import com.spontaneous.server.model.response.BaseResponse;
import com.spontaneous.server.model.response.ResponseCodes;
import com.spontaneous.server.service.FacebookService;
import com.spontaneous.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Itai on 31-May-15.
 */

@RestController
@RequestMapping(value = "/hello")
public class HelloWorldController extends BaseComponent {

    @Autowired
    private UserService mUserService;

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public BaseResponse<String> greet(@PathVariable String name) {
        BaseResponse<String> response = new BaseResponse<>(ResponseCodes.SUCCESS, "Hello, " + name + "!");

        mLogger.info("Received request from {}", name);

        return response;
    }

}
