package com.example.jwt_security_authentication.Service;

import com.example.jwt_security_authentication.dto.LoginDto;
import com.example.jwt_security_authentication.dto.RegisterDto;
import com.example.jwt_security_authentication.models.Role;
import com.example.jwt_security_authentication.models.User;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    String authenticate(LoginDto loginDto);
    ResponseEntity<?> register(RegisterDto registerDto);
    Role saveRole(Role role);
    User saveUser(User user);
}
