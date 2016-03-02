package com.spontaneous.server.handler;

import com.spontaneous.server.model.entity.User;
import com.spontaneous.server.model.entity.representational.UserAccountRO;
import com.spontaneous.server.model.entity.representational.UserProfileRO;
import com.spontaneous.server.model.request.FacebookLoginRequest;
import com.spontaneous.server.service.UserService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.ApiException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The handler layer is between the controller layer, and the service layer, and it's purpose is to translate data objects to representational object.
 */
@Service
public class UserHandler {

    private final UserService mUserService;

    @Autowired
    public UserHandler(UserService mUserService) {
        this.mUserService = mUserService;
    }

    /**
     * Return the {@link UserAccountRO} of the user with given id.
     *
     * @param id The given id.
     * @return The account of the user.
     * @throws ServiceException In case no such user is found.
     */
    public UserAccountRO getUserById(long id) throws ServiceException {
        return mUserService.getUserById(id).createUserAccount();
    }

    /**
     * Return the {@link UserProfileRO} of the user with given email.
     *
     * @param email The given email.
     * @return The user profile.
     * @throws ServiceException In case no such user is found.
     */
    public UserProfileRO getUserByEmail(String email) throws ServiceException {
        return mUserService.getUserByEmail(email).createUserProfile();
    }

    /**
     * Login a user given his Facebook User Id and Facebook Token.
     *
     * @param loginRequest The Facebook login request entity.
     * @return {@code UserAccountRO} of the logged in user.
     */
    public UserAccountRO login(FacebookLoginRequest loginRequest) throws ApiException {
        return mUserService.login(loginRequest).createUserAccount();
    }

    /**
     * Updates a user's GCM token.
     *
     * @param id    Id of the user.
     * @param token The updated token.
     * @return The updated user entity.
     * @throws ServiceException In case there is no user with the given id.
     */
    public UserAccountRO updateGcmToken(long id, String token) throws ServiceException {
        return mUserService.updateGcmToken(id, token).createUserAccount();
    }

    /**
     * Get a list of friends of the user using Spontaneous.
     *
     * @param id Id of the given user.
     * @return The {@link UserProfileRO} list of Facebook friends of the user using Spontaneous.
     */
    public List<UserProfileRO> getUserFriends(long id) {
        return mUserService.getUserFriends(id)
                .stream()
                .map(User::createUserProfile)
                .collect(Collectors.toList());
    }

}
