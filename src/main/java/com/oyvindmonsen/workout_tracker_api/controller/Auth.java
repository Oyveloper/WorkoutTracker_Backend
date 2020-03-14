package com.oyvindmonsen.workout_tracker_api.controller;

import com.oyvindmonsen.workout_tracker_api.model.AuthenticationResponse;
import com.oyvindmonsen.workout_tracker_api.model.User;
import com.oyvindmonsen.workout_tracker_api.model.UserRepository;
import com.oyvindmonsen.workout_tracker_api.services.MyUserDetailsService;
import com.oyvindmonsen.workout_tracker_api.util.JWTUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicReference;


@RestController
@RequestMapping("/auth")
public class Auth {

    @Autowired
    UserRepository userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    JWTUtil jwtUtil;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @PostMapping("/signup")
    @ResponseBody
    ResponseEntity<String> signup(@RequestParam String email, @RequestParam String name, @RequestParam String password) throws JSONException {
        JSONObject response = new JSONObject();



        AtomicReference<Boolean> foundDuplicate = new AtomicReference<>(false);
        userRepo.findAll().forEach(user -> {

            if (user.getEmail().equals(email)) {

                foundDuplicate.set(true);
            }
        });

        if (foundDuplicate.get()) {
            response.put("status", "error");
            response.put("cause", "User with email " + email + "already exists");

        } else {

            User new_user = new User();
            new_user.setName(name);
            new_user.setEmail(email);

            String passwordHash = passwordEncoder.encode(password);
            new_user.setPasswordHash(passwordHash);


            userRepo.save(new_user);

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            final String jwt = jwtUtil.generateToken(userDetails);

            response.put("status", "success");

            response.put("jwt", jwt);



        }

        return ResponseEntity.ok().body(response.toString());
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) throws Exception{
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));



    }


}
