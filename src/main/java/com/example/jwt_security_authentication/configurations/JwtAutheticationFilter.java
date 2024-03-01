package com.example.jwt_security_authentication.configurations;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAutheticationFilter extends OncePerRequestFilter {

    private final JwtUtilities jwtUtilities;
    private final CustomerUserDetailsService customerUserDetailsService;

    @Override
    protected void doFilterInternal (@NonNull HttpServletRequest request,
                                     @NonNull HttpServletResponse response,
                                     @NonNull FilterChain filterChain) throws ServletException, IOException{
        String token = jwtUtilities.getToken(request);
        //make sure the authorization header from our request is not null and that it starts with the word bearer
        if (token != null && jwtUtilities.validateToken(token)){
            //validate it and parse the username from it
            //extract our JWT from authorization header and user a dunction from the jwtService class called extractUsername to extract the value of the user email from the JWT
            String email = jwtUtilities.extractUsername(token);
            //create an authentication object and set the current UserDetails in security context.
            UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);

            if (userDetails != null){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),null,userDetails.getAuthorities());
                log.info("authenticated user with email: {}",email);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
