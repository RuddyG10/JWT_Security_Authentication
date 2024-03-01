package com.example.jwt_security_authentication.controllers;

import com.example.jwt_security_authentication.Service.IUserService;
import com.example.jwt_security_authentication.dto.LoginDto;
import com.example.jwt_security_authentication.dto.RegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {

    private final IUserService iUserService;
    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody RegisterDto registerDto){
        return iUserService.register(registerDto);
    }
    @PostMapping("/authenticate")
    public String authenticate(@RequestBody LoginDto loginDto){
        return iUserService.authenticate(loginDto);
    }
}
