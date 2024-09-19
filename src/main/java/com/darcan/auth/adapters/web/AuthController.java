package com.darcan.auth.adapters.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @GetMapping("/user/{name}")
    public ResponseEntity<String> login(@PathVariable String name) {
        return ResponseEntity.ok("Hola " + name);
    }
    
}
