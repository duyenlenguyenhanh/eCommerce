package com.example.authcourse.controllers;

import com.example.authcourse.security.Authorization;
import com.example.authcourse.model.persistence.Cart;
import com.example.authcourse.model.persistence.User;
import com.example.authcourse.model.persistence.UserOrder;
import com.example.authcourse.model.persistence.repositories.OrderRepository;
import com.example.authcourse.model.persistence.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
@Transactional
public class OrderController {
    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    private final Logger logger = LogManager.getLogger(OrderController.class);

    public OrderController(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/submit/{username}")
    public ResponseEntity<UserOrder> submit(@PathVariable String username) {
        logger.info("submit - Start...");
        logger.info("submit - request payload: username = {}", username);

        if (!Authorization.checkPersonalRight(username)) {
            logger.error("submit - Not allowed");
            logger.info("submit - End");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            logger.error("submit - User not found");
            logger.info("submit - End");
            return ResponseEntity.notFound().build();
        }

        Cart cart = optionalUser.get().getCart();
        cart.setUser(optionalUser.get());

        UserOrder order = UserOrder.createFromCart(cart);
        orderRepository.save(order);

        logger.info("submit - Success");
        logger.info("submit - End");
        return ResponseEntity.ok(order);
    }

    @GetMapping("/history/{username}")
    public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
        logger.info("getOrdersForUser - Start...");
        logger.info("getOrdersForUser - request payload: username = {}", username);

        if (!Authorization.checkPersonalRight(username)) {
            logger.error("getOrdersForUser - Not allowed");
            logger.info("getOrdersForUser - End");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            logger.error("getOrdersForUser - User not found");
            logger.info("getOrdersForUser - End");
            return ResponseEntity.notFound().build();
        }

        List<UserOrder> orders = orderRepository.findByUser(optionalUser.get());

        logger.info("getOrdersForUser - Success");
        logger.info("getOrdersForUser - End");
        return ResponseEntity.ok(orders);
    }
}
