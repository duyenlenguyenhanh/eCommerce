package com.example.authcourse.controllers;

import com.example.authcourse.model.persistence.Cart;
import com.example.authcourse.model.persistence.User;
import com.example.authcourse.model.requests.CreateUserRequest;
import com.example.authcourse.model.requests.ModifyCartRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.transaction.Transactional;
import java.util.Collections;

@Transactional
@SpringBootTest
public class CartControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private CartController cartController;

    @Test
    void addToCartSuccess() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("duyenlnh", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("duyenlnh");
        createUserRequest.setPassword("Pass.123");
        createUserRequest.setConfirmPassword("Pass.123");
        ResponseEntity<User> responseEntity = userController.createUser(createUserRequest);

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("duyenlnh");
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setQuantity(15);
        ResponseEntity<Cart> cartResponseEntity = cartController.addToCart(modifyCartRequest);

        Assertions.assertEquals(200, cartResponseEntity.getStatusCodeValue());
    }

    @Test
    void addToCartFailForbidden() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("duyenlnh", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("duyenlnh");
        createUserRequest.setPassword("Pass.123");
        createUserRequest.setConfirmPassword("Pass.123");
        ResponseEntity<User> responseEntity = userController.createUser(createUserRequest);

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("dungdp");
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setQuantity(15);
        ResponseEntity<Cart> cartResponseEntity = cartController.addToCart(modifyCartRequest);

        Assertions.assertEquals(403, cartResponseEntity.getStatusCodeValue());
    }

    @Test
    void addToCartFailItemNotFound() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("duyenlnh", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("duyenlnh");
        createUserRequest.setPassword("Pass.123");
        createUserRequest.setConfirmPassword("Pass.123");
        ResponseEntity<User> responseEntity = userController.createUser(createUserRequest);

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("duyenlnh");
        modifyCartRequest.setItemId(-1);
        modifyCartRequest.setQuantity(15);
        ResponseEntity<Cart> cartResponseEntity = cartController.addToCart(modifyCartRequest);

        Assertions.assertEquals(404, cartResponseEntity.getStatusCodeValue());
    }

    @Test
    void addToCartFailUserNotFound() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("duyenlnh", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("duyenlnh");
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setQuantity(15);
        ResponseEntity<Cart> cartResponseEntity = cartController.addToCart(modifyCartRequest);

        Assertions.assertEquals(404, cartResponseEntity.getStatusCodeValue());
    }

    @Test
    void removeFromCartSuccess() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("duyenlnh", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("duyenlnh");
        createUserRequest.setPassword("Pass.123");
        createUserRequest.setConfirmPassword("Pass.123");
        ResponseEntity<User> responseEntity = userController.createUser(createUserRequest);

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("duyenlnh");
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setQuantity(15);
        cartController.addToCart(modifyCartRequest);

        ModifyCartRequest modifyCartRequestUpdate = new ModifyCartRequest();
        modifyCartRequestUpdate.setUsername("duyenlnh");
        modifyCartRequestUpdate.setItemId(1);
        modifyCartRequestUpdate.setQuantity(5);
        ResponseEntity<Cart> cartResponseEntity = cartController.removeFromCart(modifyCartRequestUpdate);

        Assertions.assertEquals(200, cartResponseEntity.getStatusCodeValue());
    }

    @Test
    void removeFromCartFailForbidden() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("duyenlnh", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("duyenlnh");
        createUserRequest.setPassword("Pass.123");
        createUserRequest.setConfirmPassword("Pass.123");
        ResponseEntity<User> responseEntity = userController.createUser(createUserRequest);

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("duyenlnh");
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setQuantity(15);
        cartController.addToCart(modifyCartRequest);

        ModifyCartRequest modifyCartRequestUpdate = new ModifyCartRequest();
        modifyCartRequestUpdate.setUsername("duydp");
        modifyCartRequestUpdate.setItemId(1);
        modifyCartRequestUpdate.setQuantity(5);
        ResponseEntity<Cart> cartResponseEntity = cartController.removeFromCart(modifyCartRequestUpdate);

        Assertions.assertEquals(403, cartResponseEntity.getStatusCodeValue());
    }

    @Test
    void removeFromCartFailUserNotFound() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("duyenlnh", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ModifyCartRequest modifyCartRequestUpdate = new ModifyCartRequest();
        modifyCartRequestUpdate.setUsername("duyenlnh");
        modifyCartRequestUpdate.setItemId(1);
        modifyCartRequestUpdate.setQuantity(5);
        ResponseEntity<Cart> cartResponseEntity = cartController.removeFromCart(modifyCartRequestUpdate);

        Assertions.assertEquals(404, cartResponseEntity.getStatusCodeValue());
    }

    @Test
    void removeFromCartFailItemNotFound() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("duyenlnh", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("duyenlnh");
        createUserRequest.setPassword("Pass.123");
        createUserRequest.setConfirmPassword("Pass.123");
        ResponseEntity<User> responseEntity = userController.createUser(createUserRequest);

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("duyenlnh");
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setQuantity(15);
        cartController.addToCart(modifyCartRequest);

        ModifyCartRequest modifyCartRequestUpdate = new ModifyCartRequest();
        modifyCartRequestUpdate.setUsername("duyenlnh");
        modifyCartRequestUpdate.setItemId(-1);
        modifyCartRequestUpdate.setQuantity(5);
        ResponseEntity<Cart> cartResponseEntity = cartController.removeFromCart(modifyCartRequestUpdate);

        Assertions.assertEquals(404, cartResponseEntity.getStatusCodeValue());
    }
}