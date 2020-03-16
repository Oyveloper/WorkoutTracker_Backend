package com.oyvindmonsen.workout_tracker_api.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User [] findAllByEmail(String email);

    User findByEmail(String email);
}
