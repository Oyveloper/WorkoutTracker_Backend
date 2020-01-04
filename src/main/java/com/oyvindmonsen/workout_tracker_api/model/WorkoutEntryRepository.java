package com.oyvindmonsen.workout_tracker_api.model;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutEntryRepository extends CrudRepository<WorkoutEntry, Integer> {

    WorkoutEntry [] findAllByUserId(int userId);

}
