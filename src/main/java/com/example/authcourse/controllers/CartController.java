package com.example.authcourse.controllers;

import com.example.authcourse.model.persistence.Cart;
import com.example.authcourse.model.persistence.Item;
import com.example.authcourse.model.persistence.User;
import com.example.authcourse.model.persistence.repositories.CartRepository;
import com.example.authcourse.model.persistence.repositories.ItemRepository;
import com.example.authcourse.model.persistence.repositories.UserRepository;
import com.example.authcourse.model.requests.ModifyCartRequest;
import com.example.authcourse.security.Authorization;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final UserRepository userRepository;

    private final CartRepository cartRepository;

    private final ItemRepository itemRepository;

    private final Logger logger = LogManager.getLogger(CartController.class);

    public CartController(UserRepository userRepository, CartRepository cartRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
    }

    @PostMapping("/addToCart")
    public ResponseEntity<Cart> addToCart(@RequestBody ModifyCartRequest request) {
        logger.info("addToCart: Start...");
        try {
            logger.info("addToCart: request payload {}", new ObjectMapper().writer().writeValueAsString(request));
        } catch (JsonProcessingException e) {
            logger.error("addToCart: Server error");
            logger.info("addToCart: End");
            throw new RuntimeException(e);
        }
        ResponseEntity<Cart> responseEntity;
        if (!Authorization.checkPersonalRight(request.getUsername())) {
            logger.error("addToCart: Forbidden");
            logger.info("addToCart: End");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());

        if (!optionalUser.isPresent()) {
            logger.error("addToCart: User not found");
            logger.info("addToCart: End");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<Item> optionalItem = itemRepository.findById(request.getItemId());
        if (!optionalItem.isPresent()) {
            logger.error("addToCart: Item not found");
            logger.info("addToCart: End");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Cart cart = optionalUser.get().getCart();
        IntStream.range(0, request.getQuantity())
                .forEach(i -> cart.addItem(optionalItem.get()));
        cartRepository.save(cart);

        logger.info("addToCart: Success");
        logger.info("addToCart: End");
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/removeFromCart")
    public ResponseEntity<Cart> removeFromCart(@RequestBody ModifyCartRequest request) {
        logger.info("removeFromCart: Start...");
        try {
            logger.info("removeFromCart: request payload {}", new ObjectMapper().writer().writeValueAsString(request));
        } catch (JsonProcessingException e) {
            logger.error("addToCart: Server error");
            logger.info("addToCart: End");
            throw new RuntimeException(e);
        }

        if (!Authorization.checkPersonalRight(request.getUsername())) {
            logger.error("removeFromCart: Forbidden");
            logger.info("removeFromCart: End");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());

        if (!optionalUser.isPresent()) {
            logger.error("removeFromCart: User not found");
            logger.info("removeFromCart: End");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<Item> optionalItem = itemRepository.findById(request.getItemId());
        if (!optionalItem.isPresent()) {
            logger.error("removeFromCart: Item not found");
            logger.info("removeFromCart: End");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Cart cart = optionalUser.get().getCart();
        IntStream.range(0, request.getQuantity())
                .forEach(i -> cart.removeItem(optionalItem.get()));
        cartRepository.save(cart);

        logger.info("removeFromCart: Success");
        logger.info("removeFromCart: End");
        return ResponseEntity.ok(cart);
    }

}
