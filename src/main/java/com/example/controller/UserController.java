package com.example.controller;

import com.example.config.BaseComponent;
import com.example.model.entity.User;
import com.example.model.request.LoginRequest;
import com.example.model.response.BaseResponse;
import com.example.model.response.ResponseCodes;
import com.example.service.UserService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by eidan on 5/23/15.
 */
@RestController
@RequestMapping(value = "/users")
public class UserController extends BaseComponent {

    @Autowired
    private UserService mUserService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResponse login(@RequestBody LoginRequest loginRequest) {
        try {
            mLogger.info("LoginRequest = {}", loginRequest);
            User user = mUserService.login(loginRequest.getFacebookUserId(), loginRequest.getFacebookToken());
            mLogger.info("Login response = {}", user);
            return new BaseResponse<>(ResponseCodes.SUCCESS, user);
        } catch(ServiceException e) {
            return new BaseResponse<>(ResponseCodes.INTERNAL_ERROR, e.getMessage());
        }
    }

    @RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
    public BaseResponse findUserById(@PathVariable long id) {
        User user = mUserService.getUserById(id);

        return new BaseResponse<User>(ResponseCodes.SUCCESS, user);
    }
}
