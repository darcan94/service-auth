package com.darcan.auth.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.darcan.auth.domain.models.UserEntity;
import com.darcan.auth.domain.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public UserEntity register(UserEntity user){
        if (userRepository.existsByNameOrEmail(user.getName(), user.getEmail())) {
            throw new RuntimeException("Username or email already exists");
        }

        user.setActive(true);

        return userRepository.save(user);
    }
}
