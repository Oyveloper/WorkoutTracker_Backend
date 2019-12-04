package com.oyvindmonsen.workout_tracker_api.controller;

import com.oyvindmonsen.workout_tracker_api.model.Workout;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class GreetingController {

    @GetMapping("/info")
    Workout info() {

        Workout test_workout = new Workout();
        test_workout.setName("Situps");
        test_workout.setCount(2);
        test_workout.setDescription("Hell");


        return test_workout;
    }

}
