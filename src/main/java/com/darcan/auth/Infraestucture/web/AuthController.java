package com.darcan.auth.Infraestucture.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darcan.auth.Infraestucture.security.JwtUtil;
import com.darcan.auth.application.services.UserService;
import com.darcan.auth.domain.dto.LoginDto;
import com.darcan.auth.domain.models.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(
            loginDto.username(), 
            loginDto.password()
        );
        Authentication authentication = this.authenticationManager.authenticate(login);

        System.out.println(authentication.isAuthenticated());
        System.out.println(authentication.getPrincipal());

        String jwt = this.jwtUtil.create(loginDto.username());

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
    }    

    @PostMapping("/signup")
    public ResponseEntity<Void> register(@RequestBody UserEntity user) {
        UserEntity registeredUser = userService.register(user);

        User userDetails = (User) this.userDetailsService.loadUserByUsername(registeredUser.getName());

        UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(userDetails, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = this.jwtUtil.create(registeredUser.getName());
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
    } 
}
