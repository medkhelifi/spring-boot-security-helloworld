package com.medkhelifi.tutorials.springsecurityhelloworld.service;

import com.medkhelifi.tutorials.springsecurityhelloworld.models.Role;
import com.medkhelifi.tutorials.springsecurityhelloworld.models.User;
import com.medkhelifi.tutorials.springsecurityhelloworld.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository users;

    public CustomUserDetailsService(UserRepository users) {
        this.users = users;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        try {
            user = (User) this.users.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());
        return buildUserForAuthentication(user, authorities);
    }

    private org.springframework.security.core.userdetails.User buildUserForAuthentication(User user,
                                                                                          List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),true, true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(List<Role> userRoles) {

        Set<GrantedAuthority> setAuths = new HashSet<>();
        // Build user's authorities
        for (Role userRole : userRoles) {
            setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
        }
        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

        return Result;
    }
}
