package com.example.keyspring.repository;

import com.example.keyspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide CRUD operations and custom query methods
 * for the {@link User} entity.
 *
 * @author Arthur Artugue
 * @version 1.0
 * @since 2024-12-21
 * @modified 2024-12-24
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email address.
     *
     * @param email the email address to search for.
     * @return an {@link Optional} containing the found {@link User}, or empty if no user is found.
     */
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by their first name.
     *
     * @param firstName the first name to search for.
     * @return an {@link Optional} containing the found {@link User}, or empty if no user is found.
     */
    @Query("SELECT u FROM User u WHERE u.first_name = ?1")
    Optional<List<User>> findByFirstName(String firstName);

    /**
     * Finds a user by their last name.
     *
     * @param lastName the last name to search for.
     * @return an {@link Optional} containing the found {@link User}, or empty if no user is found.
     */
    @Query("SELECT u FROM User u WHERE u.last_name = ?1")
    Optional<List<User>> findByLastName(String lastName);

    /**
     * Finds a user by their unique ID.
     *
     * @param uniqueId the unique ID to search for.
     * @return an {@link Optional} containing the found {@link User}, or empty if no user is found.
     */
    @Query("SELECT u FROM User u WHERE u.unique_id = ?1")
    Optional<User> findByUniqueId(String uniqueId);
}
