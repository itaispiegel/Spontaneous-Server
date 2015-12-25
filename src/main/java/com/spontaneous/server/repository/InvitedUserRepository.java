package com.spontaneous.server.repository;

import com.spontaneous.server.model.entity.InvitedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * This class is used for data access with the database.
 */
@RepositoryRestResource(excerptProjection = InvitedUser.InvitedUserProjection.class)
public interface InvitedUserRepository extends JpaRepository<InvitedUser, Long> {
}
