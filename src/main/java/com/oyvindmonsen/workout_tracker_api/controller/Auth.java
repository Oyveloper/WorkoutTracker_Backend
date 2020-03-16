package com.oyvindmonsen.workout_tracker_api.controller;

import com.oyvindmonsen.workout_tracker_api.model.User;
import com.oyvindmonsen.workout_tracker_api.model.UserRepository;
import com.oyvindmonsen.workout_tracker_api.services.MyUserDetailsService;
import com.oyvindmonsen.workout_tracker_api.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    ApiResponse signup(@RequestParam String email, @RequestParam String name, @RequestParam String password) {
        ApiResponse response = new ApiResponse();



        AtomicReference<Boolean> foundDuplicate = new AtomicReference<>(false);
        userRepo.findAll().forEach(user -> {

            if (user.getEmail().equals(email)) {

                foundDuplicate.set(true);
            }
        });

        if (foundDuplicate.get()) {
            response.setStatus("error");
            response.addData("cause", "User with email " + email + " already exists");

        } else {

            User new_user = new User();
            new_user.setName(name);
            new_user.setEmail(email);

            String passwordHash = passwordEncoder.encode(password);
            new_user.setPasswordHash(passwordHash);


            userRepo.save(new_user);

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            final String jwt = jwtUtil.generateToken(userDetails);

            response.setStatus("success");

            response.addData("jwt", jwt);



        }

        return response;
    }


    @PostMapping("/login")
    public ApiResponse login(@RequestParam String email, @RequestParam String password){

        ApiResponse response = new ApiResponse();

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );
            response.setStatus("success");

            final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            final String jwt = jwtUtil.generateToken(userDetails);

            response.addData("jwt", jwt);
        } catch (BadCredentialsException e) {
            response.setStatus("error");
            response.addData("cause", "Wrong email or password");
        }

        return response;

    }

    @GetMapping("/isAuthenticated")
    public boolean isAuthenticated(@RequestHeader (name="Authorization") String auth) {

        String jwt = null;
        String username = null;

        if (auth != null) {
            jwt = auth;
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            return jwtUtil.validateToken(jwt, userDetails);

        }

        return false;

    }


}
