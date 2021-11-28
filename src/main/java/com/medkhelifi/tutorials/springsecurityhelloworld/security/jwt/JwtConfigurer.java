package com.medkhelifi.tutorials.springsecurityhelloworld.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * The type Jwt configurer.
 */
@Component
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    /**
     * The Jwt token filter.
     */
    JwtTokenFilter jwtTokenFilter;

    public JwtConfigurer(JwtTokenFilter jwtTokenFilter){
        this.jwtTokenFilter = jwtTokenFilter;
    }



    @Override
    public void configure(HttpSecurity http) throws Exception {
        SecurityFilterExceptionHandler securityFilterExceptionHandler = new SecurityFilterExceptionHandler();
        http
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(securityFilterExceptionHandler, JwtTokenFilter.class)
        ;
    }

}
