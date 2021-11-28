package com.medkhelifi.tutorials.springsecurityhelloworld.controller;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}
