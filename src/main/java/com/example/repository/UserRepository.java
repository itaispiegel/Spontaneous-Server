package com.example.repository;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

/**
 * This class is used for data access with the database.
 */

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
    User findByFacebookUserId(@Param(value = "facebookUserId") String facebookUserId);
}
