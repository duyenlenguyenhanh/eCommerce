package com.example.authcourse.service;

import com.example.authcourse.model.persistence.User;
import com.example.authcourse.model.requests.CreateUserRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<User> findById(Long id);

    ResponseEntity<User> findByUserName(String username);

    ResponseEntity<User> createUser(CreateUserRequest createUserRequest);
}
