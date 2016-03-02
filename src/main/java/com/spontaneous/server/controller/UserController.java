package com.spontaneous.server.controller;

import com.spontaneous.server.handler.UserHandler;
import com.spontaneous.server.model.entity.representational.UserAccountRO;
import com.spontaneous.server.model.request.FacebookLoginRequest;
import com.spontaneous.server.model.response.BaseResponse;
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
    private final UserHandler mUserHandler;

    @Autowired
    public UserController(UserHandler userHandler) {
        mLogger = LoggerFactory.getLogger(this.getClass());
        mUserHandler = userHandler;
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

            UserAccountRO user = mUserHandler.login(loginRequest);

            mLogger.info("Login response = {}", user);
            return new BaseResponse<>(user);

        } catch (ApiException e) {
            mLogger.error(e.getMessage());
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
    public BaseResponse findUserById(@RequestParam("id") long id) {

        try {
            mLogger.info("Fetching user with id #{}", id);
            return new BaseResponse<>(mUserHandler.getUserById(id));
        } catch (ServiceException e) {
            mLogger.error(e.getMessage());
            return new BaseResponse<>(e.getMessage(), BaseResponse.INTERNAL_ERROR);
        }
    }

    /**
     * Update a user's GCM token.
     *
     * @param id    Id of the user.
     * @param token The updated token.
     * @return The user instance.
     */
    @RequestMapping(value = "/updateGCM", method = RequestMethod.GET)
    public BaseResponse updateUserGcmToken(@RequestParam("id") long id, @RequestParam("token") String token) {
        try {
            mLogger.info("Updating gcm token for user with id #{}", id);
            return new BaseResponse<>(mUserHandler.updateGcmToken(id, token));
        } catch (ServiceException e) {
            mLogger.error(e.getMessage());
            return new BaseResponse<>(e.getMessage(), BaseResponse.INTERNAL_ERROR);
        }
    }

    /**
     * Get the list of friends of the user.
     *
     * @param id The id of the user.
     * @return {@link BaseResponse} representing the result of the request.
     */
    @RequestMapping(value = "/friends", method = RequestMethod.GET)
    public BaseResponse getUsersFriends(@RequestParam("id") long id) {

        try {
            mLogger.info("Fetching list of friends for user with id #{}", id);
            return new BaseResponse<>(mUserHandler.getUserFriends(id));
        } catch (ServiceException e) {
            mLogger.error(e.getMessage());
            return new BaseResponse<>(e.getMessage(), BaseResponse.INTERNAL_ERROR);
        }
    }
}
