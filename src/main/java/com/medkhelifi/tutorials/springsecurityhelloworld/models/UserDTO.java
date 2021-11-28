package com.medkhelifi.tutorials.springsecurityhelloworld.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String email;
    private String token;
}
