package com.medkhelifi.tutorials.springsecurityhelloworld.service;

import com.medkhelifi.tutorials.springsecurityhelloworld.models.User;
import com.medkhelifi.tutorials.springsecurityhelloworld.models.UserDTO;
import org.springframework.stereotype.Service;

public interface UserService {
    UserDTO authenticate (String userName, String password);
    User findByEmail(String email) ;
    User getCurrentUser( );
}
