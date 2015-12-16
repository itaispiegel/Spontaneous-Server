package com.spontaneous.server.controller;

import com.spontaneous.server.config.BaseComponent;
import com.spontaneous.server.model.entity.User;
import com.spontaneous.server.model.request.FacebookLoginRequest;
import com.spontaneous.server.model.response.BaseResponse;
import com.spontaneous.server.service.UserService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * This is a REST controller for user operations.
 */
@RestController
@RequestMapping(value = "/API/users")
public class UserController extends BaseComponent {

    @Autowired
    private UserService mUserService;

    /**
     * Login the user given a FacebookLoginRequest.
     *
     * @param loginRequest - Facebook login request.
     * @return Response saying whether the login was successful, and the user details.
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResponse login(@RequestBody FacebookLoginRequest loginRequest) {

        try {

            mLogger.info("FacebookLoginRequest = {}", loginRequest);

            User user = mUserService.login(loginRequest.getFacebookUserId(), loginRequest.getFacebookToken());

            mLogger.info("Login response = {}", user);
            return new BaseResponse<>(BaseResponse.SUCCESS, user);

        } catch (ServiceException e) {
            return new BaseResponse<>(BaseResponse.INTERNAL_ERROR, e.getMessage());
        }
    }

    /**
     * Find a user based on the given id.
     * @param id of the user.
     * @return The user instance.
     */
    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
    public BaseResponse findUserById(@PathVariable long id) {
        User user = mUserService.getUserById(id);

        return new BaseResponse<>(BaseResponse.SUCCESS, user);
    }
}
