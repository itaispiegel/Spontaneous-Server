package com.spontaneous.server.controller;

import com.spontaneous.server.model.entity.User;
import com.spontaneous.server.model.request.FacebookLoginRequest;
import com.spontaneous.server.model.response.BaseResponse;
import com.spontaneous.server.service.UserService;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.ApiException;
import org.springframework.web.bind.annotation.*;

/**
 * This is a REST controller for user operations.
 */
@RestController
@RequestMapping(value = "/API/users")
public class UserController {

    private final Logger mLogger;

    private final UserService mUserService;

    @Autowired
    public UserController(UserService userService) {
        mLogger = LoggerFactory.getLogger(this.getClass());
        mUserService = userService;
    }

    /**
     * Login the user with a given FacebookLoginRequest.
     * In case of {@link ServiceException}/{@link ApiException} return the error.
     *
     * @param loginRequest - Facebook login request.
     * @return Response saying whether the login was successful, and the user details.
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResponse login(@RequestBody FacebookLoginRequest loginRequest) {

        try {

            mLogger.info("FacebookLoginRequest = {}", loginRequest);

            User user = mUserService.login(loginRequest);

            mLogger.info("Login response = {}", user);
            return new BaseResponse<>(user);

        } catch (ApiException e) {
            return new BaseResponse<>(e.getMessage(), BaseResponse.INTERNAL_ERROR);
        }
    }

    /**
     * Find a user based on the given id.
     * In case of {@link NullPointerException} (user not found), return the error.
     *
     * @param id of the user.
     * @return The user instance.
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public BaseResponse findUserById(@RequestParam("user_id") long id) {

        try {
            return new BaseResponse<>(mUserService.getUserById(id));
        } catch (ServiceException e) {
            return new BaseResponse<>(e.getMessage(), BaseResponse.INTERNAL_ERROR);
        }
    }
}
