package com.example.keyspring.repository;

import com.example.keyspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.first_name = ?1")
    Optional<User> findByFirstName(String firstname);

    @Query("SELECT u FROM User u WHERE u.last_name = ?1")
    Optional<User> findByLastName(String lastname);

    @Query("SELECT u FROM User u WHERE u.unique_id = ?1")
    Optional<User> findByUniqueId(String uniqueId);

}
