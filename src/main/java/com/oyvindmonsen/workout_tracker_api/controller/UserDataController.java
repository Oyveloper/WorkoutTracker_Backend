package com.oyvindmonsen.workout_tracker_api.controller;


import com.oyvindmonsen.workout_tracker_api.model.*;
import com.oyvindmonsen.workout_tracker_api.services.MyUserDetailsService;
import com.oyvindmonsen.workout_tracker_api.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

@RestController
public class UserDataController {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    private WorkoutEntryRepository entryRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @PostMapping("/addWorkoutEntry")
    ResponseEntity<?> addWorkoutEntry(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String name,
            @RequestParam String progression,
            @RequestParam (value="date") @DateTimeFormat(pattern = "ddMMyy") Date date,
            @RequestParam float ammount
            ) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.extractUsername(jwt));
        User user = userRepo.findByEmail(userDetails.getUsername());
        WorkoutEntry entry = new WorkoutEntry();
        entry.setUserId(user.getId());
        entry.setWorkoutName(name);
        entry.setDate(date);
        entry.setAmmount(ammount);
        entry.setProgression(progression);

        System.out.println(entry.getWorkoutName());

        entryRepo.save(entry);

        return ResponseEntity.ok().body("Success");

    }

    @GetMapping("/workoutlist")
    ResponseEntity<?> workoutList(@RequestHeader("Authorization") String jwt) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.extractUsername(jwt));
        User user = userRepo.findByEmail(userDetails.getUsername());


        ArrayList<Workout> workouts = new ArrayList<>();


        WorkoutEntry[] entries = entryRepo.findAllByUserId(user.getId());

        for (WorkoutEntry entry : entries) {
            boolean added = false;
            for (int j = 0; j < workouts.size() && !added; j++) {

                Workout workout = workouts.get(j);
                if (workout.getName().equals(entry.getWorkoutName())) {
                    workout.addEntry(entry);
                    added = true;
                    if (!workout.getProgressions().contains(entry.getProgression())) {
                        workout.addProgression(entry.getProgression());
                    }
                }
            }


            if (!added) {
                Workout workout = new Workout();
                workout.setName(entry.getWorkoutName());
                workout.setMeasurement(entry.getMeasurement());
                ArrayList<String> progressions = new ArrayList<>();
                progressions.add(entry.getProgression());
                workout.setProgressions(progressions);
                workout.addEntry(entry);
                workouts.add(workout);
            }
        }


        return ResponseEntity.ok(workouts);

    }
}
