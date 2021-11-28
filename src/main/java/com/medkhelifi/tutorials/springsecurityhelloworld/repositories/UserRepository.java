package com.medkhelifi.tutorials.springsecurityhelloworld.repositories;
import com.medkhelifi.tutorials.springsecurityhelloworld.models.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;


public interface UserRepository extends PagingAndSortingRepository<User, String> {
    Optional<User> findByEmail (String email);
}
