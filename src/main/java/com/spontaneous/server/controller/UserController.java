package com.spontaneous.server.controller;

import com.spontaneous.server.model.entity.User;
import com.spontaneous.server.model.entity.representational.UserAccountRO;
import com.spontaneous.server.model.entity.representational.UserProfileRO;
import com.spontaneous.server.model.request.FacebookLoginRequest;
import com.spontaneous.server.model.response.BaseResponse;
import com.spontaneous.server.service.UserService;
import com.spontaneous.server.util.Converter;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.ApiException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

            List<UserProfileRO> friends = Converter.convertList(mUserService.getUserFriends(user.getId()), User::createUserProfile);
            UserAccountRO userAccount = user.createUserAccount(friends);

            mLogger.info("Login response = {}", userAccount);
            return new BaseResponse<>(userAccount);

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

            User user = mUserService.getUserById(id);

            List<UserProfileRO> friends = Converter.convertList(mUserService.getUserFriends(user.getId()), User::createUserProfile);
            UserAccountRO userAccount = user.createUserAccount(friends);

            return new BaseResponse<>(userAccount);
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

            User user = mUserService.updateGcmToken(id, token);

            List<UserProfileRO> friends = Converter.convertList(mUserService.getUserFriends(user.getId()), User::createUserProfile);
            UserAccountRO userAccount = user.createUserAccount(friends);

            return new BaseResponse<>(userAccount);
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

            List<UserProfileRO> friendsProfiles = Converter.convertList(mUserService.getUserFriends(id), User::createUserProfile);

            return new BaseResponse<>(friendsProfiles);
        } catch (ServiceException e) {
            mLogger.error(e.getMessage());
            return new BaseResponse<>(e.getMessage(), BaseResponse.INTERNAL_ERROR);
        }
    }
}
