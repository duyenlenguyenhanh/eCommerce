package com.example.authcourse.controllers;

import com.example.authcourse.model.persistence.Cart;
import com.example.authcourse.model.persistence.User;
import com.example.authcourse.model.persistence.UserOrder;
import com.example.authcourse.model.requests.CreateUserRequest;
import com.example.authcourse.model.requests.ModifyCartRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
class OrderControllerTest {

    @Autowired
    private OrderController orderController;

    @Autowired
    private CartController cartController;

    @Autowired
    private UserController userController;

    @Test
    void submitSuccess() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("duyenlnh", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("duyenlnh");
        createUserRequest.setPassword("Pass.123");
        createUserRequest.setConfirmPassword("Pass.123");
        ResponseEntity<User> userResponseEntity = userController.createUser(createUserRequest);

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("duyenlnh");
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setQuantity(5);
        ResponseEntity<Cart> cartResponseEntity = cartController.addToCart(modifyCartRequest);

        ResponseEntity<UserOrder> orderResponseEntity = orderController.submit("duyenlnh");
        Assertions.assertEquals(200, orderResponseEntity.getStatusCodeValue());
    }

    @Test
    void submitFailForbidden() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("duyenlnh", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseEntity<UserOrder> orderResponseEntity = orderController.submit("duydp");
        Assertions.assertEquals(403, orderResponseEntity.getStatusCodeValue());
    }

    @Test
    void submitFailUserNotFound() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("duyenlnh", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseEntity<UserOrder> orderResponseEntity = orderController.submit("duyenlnh");
        Assertions.assertEquals(404, orderResponseEntity.getStatusCodeValue());
    }

    @Test
    void getOrdersForUserSuccess() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("duyenlnh", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("duyenlnh");
        createUserRequest.setPassword("Pass.123");
        createUserRequest.setConfirmPassword("Pass.123");
        ResponseEntity<User> userResponseEntity = userController.createUser(createUserRequest);

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("duyenlnh");
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setQuantity(5);
        ResponseEntity<Cart> cartResponseEntity = cartController.addToCart(modifyCartRequest);

        ResponseEntity<UserOrder> orderResponseEntity = orderController.submit("duyenlnh");
        ResponseEntity<List<UserOrder>> listResponseEntity = orderController.getOrdersForUser("duyenlnh");
        Assertions.assertEquals(200, listResponseEntity.getStatusCodeValue());
    }

    @Test
    void getOrdersForUserFailForbidden() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("duyenlnh", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseEntity<List<UserOrder>> listResponseEntity = orderController.getOrdersForUser("duydp");
        Assertions.assertEquals(403, listResponseEntity.getStatusCodeValue());
    }

    @Test
    void getOrdersForUserFailUserN() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("duyenlnh", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseEntity<List<UserOrder>> listResponseEntity = orderController.getOrdersForUser("duyenlnh");
        Assertions.assertEquals(404, listResponseEntity.getStatusCodeValue());
    }
}