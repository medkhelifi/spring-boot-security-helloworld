package com.medkhelifi.tutorials.springsecurityhelloworld.security;

import com.medkhelifi.tutorials.springsecurityhelloworld.repositories.UserRepository;
import com.medkhelifi.tutorials.springsecurityhelloworld.security.configuration.SecurityPropertiesExtension;
import com.medkhelifi.tutorials.springsecurityhelloworld.security.jwt.JwtConfigurer;
import com.medkhelifi.tutorials.springsecurityhelloworld.security.jwt.JwtTokenFilter;
import com.medkhelifi.tutorials.springsecurityhelloworld.security.jwt.JwtTokenProvider;
import com.medkhelifi.tutorials.springsecurityhelloworld.security.jwt.SecurityFilterExceptionHandler;
import com.medkhelifi.tutorials.springsecurityhelloworld.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

@Slf4j
@EnableWebSecurity
public class WebSecurityAutoConfiguration  extends WebSecurityConfigurerAdapter {

    private final JwtConfigurer jwtConfigurer;
    private final SecurityFilterExceptionHandler securityFilterExceptionHandler;
    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }


    public WebSecurityAutoConfiguration(JwtConfigurer jwtConfigurer, SecurityFilterExceptionHandler securityFilterExceptionHandler) {
        this.jwtConfigurer = jwtConfigurer;
        this.securityFilterExceptionHandler = securityFilterExceptionHandler;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .disable()
                .cors()
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(securityFilterExceptionHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/debug/**").permitAll()
                .antMatchers("/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .apply(jwtConfigurer)
        ;
    }


}
