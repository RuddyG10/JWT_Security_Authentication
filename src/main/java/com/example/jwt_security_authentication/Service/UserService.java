package com.example.jwt_security_authentication.Service;

import com.example.jwt_security_authentication.configurations.JwtUtilities;
import com.example.jwt_security_authentication.dto.BearerToken;
import com.example.jwt_security_authentication.dto.LoginDto;
import com.example.jwt_security_authentication.dto.RegisterDto;
import com.example.jwt_security_authentication.models.Role;
import com.example.jwt_security_authentication.models.RoleName;
import com.example.jwt_security_authentication.models.User;
import com.example.jwt_security_authentication.reposiroties.RoleRepository;
import com.example.jwt_security_authentication.reposiroties.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilities jwtUtilities;

    @Override
    public Role saveRole(Role role){
        return roleRepository.save(role);
    }

    @Override
    public User saveUser(User user){
        return userRepository.save(user);
    }
    @Override
    public ResponseEntity<?> register(RegisterDto registerDto){
        if(userRepository.existsByEmail(registerDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.SEE_OTHER);
        }else {
            User user = new User();
            user.setEmail(registerDto.getEmail());
            user.setFirstName(registerDto.getFirstName());
            user.setLastName(registerDto.getLastName());
            user.setPassword(registerDto.getPassword());
            String myRole = "user";

            if (registerDto.getUserRole().equals("")||registerDto.getUserRole().equals("user")){
                myRole = "USER";
            }
            if (registerDto.getUserRole().equals("admin")){
                myRole = "ADMIN";
            }
            Role role = roleRepository.findByRoleName(RoleName.valueOf(myRole));
            user.setUserRole(registerDto.getUserRole());
            user.setRoles(Collections.singletonList(role));
            userRepository.save(user);
            String token = jwtUtilities.generateToken(registerDto.getEmail(), Collections.singletonList(role.getRoleName()));

            return new ResponseEntity<>(new BearerToken(token,"Bearer "),HttpStatus.OK);
        }
    }

    @Override
    public String authenticate(LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found."));
        List<String> rolesNames = new ArrayList<>();
        user.getRoles().forEach(r -> rolesNames.add(r.getRoleName()));
        String token = jwtUtilities.generateToken(user.getUsername(),rolesNames);

        return "User login successful! Token: "+token;
    }
}
