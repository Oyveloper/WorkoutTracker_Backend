package com.oyvindmonsen.workout_tracker_api.services;

import com.oyvindmonsen.workout_tracker_api.model.User;
import com.oyvindmonsen.workout_tracker_api.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User foundUser = userRepository.findByEmail(username);
        return new org.springframework.security.core.userdetails.User(foundUser.getEmail(), foundUser.getPasswordHash(), new ArrayList<>());
    }
}
