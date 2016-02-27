package com.spontaneous.server.service;

import com.spontaneous.server.model.entity.User;
import com.spontaneous.server.model.entity.representational.UserProfileRO;
import com.spontaneous.server.model.request.FacebookLoginRequest;
import com.spontaneous.server.repository.UserRepository;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.ApiException;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Reference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is part of the service layer of the application and is used for user manipulation in the database.
 */
@Service
public class UserService {

    private final Logger mLogger;

    private final UserRepository mUserRepository;
    private final FacebookService mFacebookService;

    @Autowired
    public UserService(UserRepository userRepository, FacebookService facebookService) {
        mLogger = LoggerFactory.getLogger(this.getClass());
        mUserRepository = userRepository;
        mFacebookService = facebookService;
    }

    /**
     * Find a user with the given id.
     *
     * @param id of the user
     * @return The user.
     * @throws ServiceException Is caught in case there no user found with the given id.
     */
    public User getUserById(long id) throws ServiceException {
        User user = mUserRepository.findOne(id);

        if (user == null) {
            throw new ServiceException(String.format("No such user with id #%d.", id));
        }

        return user;
    }

    /**
     * Find a user with the given email address.
     *
     * @param email Email address of the user to find.
     * @return The user entity.
     * @throws ServiceException Is thrown in case that no such user was found.
     */
    public User getUserByEmail(String email) throws ServiceException {
        User user = mUserRepository.findByEmail(email);

        if (user == null) {
            throw new ServiceException(String.format("No such user with the the email: %s.", email));
        }

        return user;
    }

    /**
     * Find a user with the given Facebook id.
     *
     * @param facebookUserId Facebook id of the user to find.
     * @return The user entity.
     * @throws ServiceException Is thrown in case that no such user was found.
     */
    public User getUserByFacebookId(String facebookUserId) throws ServiceException {
        User user = mUserRepository.findByFacebookUserId(facebookUserId);

        if (user == null) {
            throw new ServiceException(String.format("No such user with the Facebook id: %s", facebookUserId));
        }

        return user;
    }

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id Of the user.
     * @return true if an entity with the given id exists, false otherwise
     */
    public boolean exists(long id) {
        return mUserRepository.exists(id);
    }

    /**
     * Login a user given his Facebook User Id and Facebook Token.
     *
     * @param loginRequest The Facebook login request entity.
     * @return User details of the logged in user.
     */
    public User login(FacebookLoginRequest loginRequest) {

        try {

            String facebookUserId = loginRequest.getFacebookUserId();
            String facebookToken = loginRequest.getFacebookToken();

            //Find the user
            User user = mUserRepository.findByFacebookUserId(facebookUserId);

            //If no user is found, create a new user instance
            if (user == null) {
                user = new User.Builder()
                        .facebookUserId(facebookUserId)
                        .facebookToken(facebookToken)
                        .build();
            }

            //Set the user details from Facebook
            user = mFacebookService.setUserDetails(user, facebookToken, facebookUserId);
            return mUserRepository.save(user);
        } catch (ApiException e) {
            mLogger.trace(e.getMessage());
            throw e;
        }
    }

    /**
     * Updates a user's GCM token.
     *
     * @param id    Id of the user.
     * @param token The updated token.
     * @return The updated user entity.
     * @throws ServiceException In case there is no user with the given id.
     */
    public User updateGcmToken(long id, String token) throws ServiceException {
        User user = getUserById(id);
        user.setGcmToken(token);

        return mUserRepository.save(user);
    }

    /**
     * @param id Id of the given user.
     * @return The list of Facebook friends of the user using Spontaneous.
     */
    public List<UserProfileRO> getUserFriends(long id) throws ServiceException, ApiException {

        //Get list of friends of current user.
        PagedList<Reference> friends = mFacebookService.getUserFriends(getUserById(id).getFacebookToken());

        //Initialize a list of user profiles.
        ArrayList<UserProfileRO> friendsProfiles = new ArrayList<>(friends.size());

        //Add each user profile to the list.
        for (Reference friend : friends) {

            try {

                //Get the friend details from the database.
                UserProfileRO friendProfile = getUserByFacebookId(friend.getId())
                        .createRepresentationalObject();

                friendsProfiles.add(friendProfile);

            } catch (ServiceException e) {
                //In case there is no such user registered in the database, log the exception.
                mLogger.error(e.getMessage());
            }
        }

        return friendsProfiles;
    }

    /**
     * Gets the user profile of a user by his id.
     *
     * @param id Id of the given user.
     * @return Return {@link UserProfileRO} of the user.
     */
    public UserProfileRO getUserProfile(long id) throws ServiceException {
        return getUserById(id).createRepresentationalObject();
    }
}
