package com.medkhelifi.tutorials.springsecurityhelloworld.service;

import com.medkhelifi.tutorials.springsecurityhelloworld.models.User;
import com.medkhelifi.tutorials.springsecurityhelloworld.models.UserDTO;
import com.medkhelifi.tutorials.springsecurityhelloworld.repositories.UserRepository;
import com.medkhelifi.tutorials.springsecurityhelloworld.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDTO authenticate(String userName, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        User userEntity = (User) this.userRepository.findByEmail(userName).orElseThrow(() -> new UsernameNotFoundException("Error authentication! username " + userName + " or password incorrect"));
        String newToken = jwtTokenProvider.createToken(userName, userEntity.getRoles());
        UserDTO userDTO = new UserDTO(userEntity.getUserId(), userEntity.getEmail(), newToken);

        return userDTO;
    }

    @Override
    public User findByEmail(String email) {
        User userEntity = (User) this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Username " + email + " not found"));
        return userEntity;
    }

    @Override
    public User getCurrentUser( ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return findByEmail(username);
    }
}
