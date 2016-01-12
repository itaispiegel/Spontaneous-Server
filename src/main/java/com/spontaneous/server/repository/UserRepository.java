package com.spontaneous.server.repository;

import com.spontaneous.server.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * This class is used for data access with the database.
 */
@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Get a user entity given his Facebook user id.
     * @param facebookUserId The Facebook id of the user.
     * @return The user instance.
     */
    User findByFacebookUserId(@Param(value = "facebookUserId") String facebookUserId);

    /**
     * Get a user entity given his email address.
     * @param email Email address of the user.
     * @return The user entity.
     */
    User findByEmail(@Param(value = "email") String email);
}
