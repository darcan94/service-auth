package com.darcan.auth.applications.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.darcan.auth.domain.UserEntity;
import com.darcan.auth.domain.UserRepository;
import com.darcan.auth.domain.UserRole;

@Service
public class UserSecurityService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserEntity user = this.userRepository.findByName(name)
                        .orElseThrow(() -> new UsernameNotFoundException("User " + name + " not found."));

        String[] roles = user.getRoles()
                            .stream()
                            .map(UserRole::getRole)
                            .toArray(String[]::new);

        return User.builder()
                .username(user.getName())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }
    
}
