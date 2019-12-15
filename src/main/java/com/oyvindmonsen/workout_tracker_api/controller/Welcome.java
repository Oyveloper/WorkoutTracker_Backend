package com.oyvindmonsen.workout_tracker_api.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Welcome {

    @GetMapping("/hello")
    String hello() throws Exception {
        return "Hello";
    }
}
