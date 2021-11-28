package com.medkhelifi.tutorials.springsecurityhelloworld.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * The type Invalid jwt authentication exception.
 */
public class InvalidJwtAuthenticationException extends AuthenticationException {

    /**
     *
     * @param e
     * @param thr
     */
    public InvalidJwtAuthenticationException(String e, Throwable thr){
        super(e, thr);
    }
}
