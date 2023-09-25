package com.example.authcourse.controllers;

import com.example.authcourse.model.persistence.User;
import com.example.authcourse.model.requests.CreateUserRequest;
import com.example.authcourse.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@Transactional
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findByUserName(@PathVariable String username) {
        return userService.findByUserName(username);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest);
    }

}
