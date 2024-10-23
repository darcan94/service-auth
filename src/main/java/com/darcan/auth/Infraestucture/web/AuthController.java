package com.darcan.auth.Infraestucture.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darcan.auth.Infraestucture.security.JwtUtil;
import com.darcan.auth.application.services.UserService;
import com.darcan.auth.domain.dto.LoginDto;
import com.darcan.auth.domain.models.UserEntity;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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
    public ResponseEntity<Void> login(@RequestBody LoginDto loginDto ) {
        Authentication authentication = this.authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDto.username(), 
                loginDto.password()
            )
        );

        if(!authentication.isAuthenticated()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String jwt = this.jwtUtil.create(loginDto.username());

        ResponseCookie cookie = ResponseCookie.from("token", jwt)
            .httpOnly(true)
            .secure(true)
            .sameSite("Strict")
            .domain("darcan.dev")
            .maxAge(Duration.ofHours(1))
            .build();

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .build();
    }    

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie cookie = ResponseCookie.from("token", "")
            .httpOnly(true)
            .secure(true)
            .sameSite("Strict")
            .domain("darcan.dev")
            .maxAge(0)
            .build();

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .build();
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
