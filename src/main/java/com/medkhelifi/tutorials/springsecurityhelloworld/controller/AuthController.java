package com.medkhelifi.tutorials.springsecurityhelloworld.controller;

import com.medkhelifi.tutorials.springsecurityhelloworld.models.UserDTO;
import com.medkhelifi.tutorials.springsecurityhelloworld.repositories.UserRepository;
import com.medkhelifi.tutorials.springsecurityhelloworld.security.jwt.JwtTokenProvider;
import com.medkhelifi.tutorials.springsecurityhelloworld.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthController(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }


    @PostMapping(value= "/signing")
    public ResponseEntity<Object> signing(@RequestBody AuthenticationRequest data, HttpServletRequest servletRequest) throws Exception {
        try {
            String username = data.getUsername();
            UserDTO userEntity = this.userService.authenticate(username, data.getPassword());

            return new ResponseEntity<>(userEntity,OK );

        } catch (AuthenticationException e) {
            return new ResponseEntity<>(e.getMessage(), INTERNAL_SERVER_ERROR);
        }
    }


}
