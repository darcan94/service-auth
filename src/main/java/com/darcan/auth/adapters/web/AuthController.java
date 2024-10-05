package com.darcan.auth.adapters.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darcan.auth.adapters.config.JwtUtil;
import com.darcan.auth.applications.dto.LoginDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/user/{name}")
    public ResponseEntity<String> getUser(@PathVariable String name) {
        return ResponseEntity.ok("Hola " + name);
    }

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
    
}
