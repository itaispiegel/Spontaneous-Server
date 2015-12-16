package com.spontaneous.server.service;

import com.spontaneous.server.model.entity.User;
import com.spontaneous.server.repository.UserRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class is part of the service layer of the application and is used for user manipulation in the database.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository mUserRepository;

    @Autowired
    private FacebookService mFacebookService;

    /**
     * Find a user with the given id.
     * @param id of the user
     * @return The user.
     */
    public User getUserById(long id) {
        return mUserRepository.findOne(id);
    }

    /**
     * Login a user given his Facebook User Id and Facebook Token.
     * @return The user.
     * @throws ServiceException if there was a problem authenticating the user.
     */
    public User login(String facebookUserId, String facebookToken) throws ServiceException {
        try {

            //Find the user
            User user = mUserRepository.findByFacebookUserId(facebookUserId);

            //If no user is found, create a new user instance
            if(user == null) {
                user = new User();
                user.setFacebookUserId(facebookUserId);
                user.setFacebookToken(facebookToken);
            }

            //Set the user details from Facebook
            user = mFacebookService.setUserDetails(user, facebookToken, facebookUserId);
            return mUserRepository.save(user);
        } catch(ServiceException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
