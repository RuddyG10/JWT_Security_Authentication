package com.example.jwt_security_authentication.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicRestController {
    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome! This is public content!";
    }
}
