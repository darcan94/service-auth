package com.darcan.auth.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.darcan.auth.domain.models.UserEntity;
import com.darcan.auth.domain.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity register(UserEntity user){
        if (userRepository.existsByNameOrEmail(user.getName(), user.getEmail())) {
            throw new RuntimeException("Username or email already exists");
        }

        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
}
