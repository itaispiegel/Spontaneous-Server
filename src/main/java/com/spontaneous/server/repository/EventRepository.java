package com.spontaneous.server.repository;

import com.spontaneous.server.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * This class is used for data access with the database.
 */
@RepositoryRestResource
public interface EventRepository extends JpaRepository<Event, Long> {

    /**
     * Find an event by its title.
     *
     * @param title of the event
     * @return The event entity.
     */
    List<Event> findByTitle(@Param(value = "title") String title);

    /**
     * Retrieve a list of events relevant to the given user.
     *
     * @param id of the user.
     * @return List of events the user is invited to.
     */
    @Query(value = "SELECT events.* FROM events " +
            "INNER JOIN invited_users " +
            "ON events.id = invited_users.event_id " +
            "WHERE invited_users.user_id = :user_id", nativeQuery = true)
    List<Event> findByInvitedUser(@Param(value = "user_id") long id);
}
