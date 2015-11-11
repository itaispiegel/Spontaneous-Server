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
    User findByFacebookUserId(@Param(value = "facebookUserId") String facebookUserId);
}
