package com.example.jwt_security_authentication;

import com.example.jwt_security_authentication.Service.IUserService;
import com.example.jwt_security_authentication.models.Role;
import com.example.jwt_security_authentication.models.RoleName;
import com.example.jwt_security_authentication.reposiroties.RoleRepository;
import com.example.jwt_security_authentication.reposiroties.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class JwtSecurityAuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtSecurityAuthenticationApplication.class, args);
    }
    @Bean
    CommandLineRunner run (IUserService iUserService , RoleRepository roleRepository , UserRepository userRepository , PasswordEncoder passwordEncoder) {
        return  args -> {   iUserService.saveRole(new Role(RoleName.USER));
        iUserService.saveRole(new Role(RoleName.ADMIN));
    };
    }
}
