package com.spontaneous.server.service;

import com.spontaneous.server.model.entity.User;
import com.spontaneous.server.model.request.FacebookLoginRequest;
import com.spontaneous.server.repository.UserRepository;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.ApiException;
import org.springframework.stereotype.Service;

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
}
