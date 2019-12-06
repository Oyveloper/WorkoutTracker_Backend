package com.oyvindmonsen.workout_tracker_api.controller;

import com.oyvindmonsen.workout_tracker_api.model.User;
import com.oyvindmonsen.workout_tracker_api.model.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicReference;


@RestController
@RequestMapping("/auth")
public class Auth {

    @Autowired
    UserRepository userRepo;


    @PostMapping("/signup")
    @ResponseBody
    String signup(@RequestParam String email, @RequestParam String name, @RequestParam String password) throws JSONException {
        JSONObject response = new JSONObject();


        AtomicReference<Boolean> foundDuplicate = new AtomicReference<>(false);
        userRepo.findAll().forEach(user -> {

            if (user.getEmail().equals(email)) {

                foundDuplicate.set(true);
            }
        });

        if (foundDuplicate.get()) {


            try {
                response.accumulate("status", "error");
                response.accumulate("cause", "User with email " + email + "already exists");
            } catch (Exception e) {
                System.out.println(e);
            }

        } else {

            User new_user = new User();
            new_user.setName(name);
            new_user.setEmail(email);
            new_user.setPasswordHash(password);


            userRepo.save(new_user);

            try {
                response.accumulate("status", "success");
            } catch (Exception e) {
                System.out.println(e);
            }



        }

        return response.toString();
    }
}
