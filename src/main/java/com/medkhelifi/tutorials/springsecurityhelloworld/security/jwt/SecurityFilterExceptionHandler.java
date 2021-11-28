package com.medkhelifi.tutorials.springsecurityhelloworld.security.jwt;

import com.medkhelifi.tutorials.springsecurityhelloworld.exceptions.InvalidJwtAuthenticationException;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;


/**
 * SecurityFilterExceptionHandler handle all exceptions occurred when authentication or authorization process
 * <p>
 *     Create a filter exception handler, this filter will be register before {@link com.medkhelifi.security.jwt.JwtTokenFilter}
 * </p>
 *
 * <p>
 *     Handle also {@link org.springframework.security.config.annotation.web.builders.HttpSecurity } exceptions
 * </p>
 *
 * @author medKhelifi
 * @since 0.1.0
 */
@Slf4j
@Component
public class SecurityFilterExceptionHandler extends OncePerRequestFilter implements AuthenticationEntryPoint {

    @Override
    public void doFilterInternal(HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            log.debug("SecurityFilterExceptionHandler "+httpServletRequest.getRequestURI()+" URI is executed" );
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (AccessDeniedException    e) {
            log.error(e.getMessage()+" "+e.getLocalizedMessage());
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.getWriter().write((e.getMessage()));
        }catch (InvalidJwtAuthenticationException ex) {
            log.error(ex.getMessage()+" "+ex.getLocalizedMessage());
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.getWriter().write((ex.getMessage()));
        }catch (Exception e){
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.getWriter().write((e.getMessage()));
        }
    }

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException {
            new ResponseEntity<Object>(authException.getMessage(), UNAUTHORIZED);
    }



}
