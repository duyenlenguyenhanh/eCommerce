package com.example.authcourse.service;

import com.example.authcourse.model.persistence.Cart;
import com.example.authcourse.model.persistence.User;
import com.example.authcourse.model.persistence.repositories.CartRepository;
import com.example.authcourse.model.persistence.repositories.UserRepository;
import com.example.authcourse.model.requests.CreateUserRequest;
import com.example.authcourse.security.Authorization;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final CartRepository cartRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, CartRepository cartRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public ResponseEntity<User> findById(Long id) {
        logger.info("findById - Start...");
        logger.info("findById - request payload: id = {}", id);
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            logger.error("findById - Not found");
            logger.info("findById - End");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        String username = optionalUser.get().getUsername();
        if (!Authorization.checkPersonalRight(username)) {
            logger.error("findById - Not allowed");
            logger.info("findById - End");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        logger.info("findById - Success");
        logger.info("findById - End");
        return ResponseEntity.ok(optionalUser.get());
    }

    @Override
    public ResponseEntity<User> findByUserName(String username) {
        logger.info("findByUserName - Start...");
        logger.info("findByUserName - request payload: username = {}", username);

        if (!Authorization.checkPersonalRight(username)) {
            logger.error("findByUserName - Not allow");
            logger.info("findByUserName - End");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            logger.error("findByUserName - Not found");
            logger.info("findByUserName - End");
            return ResponseEntity.notFound().build();
        }

        logger.info("findByUserName - Success");
        logger.info("findByUserName - End");
        return ResponseEntity.ok(optionalUser.get());
    }

    @Override
    public ResponseEntity<User> createUser(CreateUserRequest createUserRequest) {
        logger.info("createUser - Start...");
        try {
            logger.info("createUser - request payload: createUserRequest = {}", new ObjectMapper().writer().writeValueAsString(createUserRequest));
        } catch (JsonProcessingException e) {
            logger.error("createUser - Server error");
            logger.info("createUser - End");
            throw new RuntimeException(e);
        }
        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        Cart cart = new Cart();
        cartRepository.save(cart);
        user.setCart(cart);

        if (createUserRequest.getPassword().length() <= 7 ||
                !createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
            logger.error("createUser - Bad request");
            logger.info("createUser - End");
            return ResponseEntity.badRequest().build();
        }

        user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
        userRepository.save(user);
        logger.info("createUser - Success");
        logger.info("createUser - End");
        return ResponseEntity.ok(user);
    }
}
