package com.oyvindmonsen.workout_tracker_api.controller;


import com.oyvindmonsen.workout_tracker_api.services.MyUserDetailsService;
import com.oyvindmonsen.workout_tracker_api.util.JWTUtil;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserDataController {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @GetMapping("/workoutlist")
    ResponseEntity<?> workoutList(@RequestHeader("Authorization") String jwt) throws Exception {

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.extractUsername(jwt));



        return ResponseEntity.ok("Working out");

    }
}
