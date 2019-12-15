package com.oyvindmonsen.workout_tracker_api.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User [] findAllByEmail(String email);

    User findByEmail(String email);
}
