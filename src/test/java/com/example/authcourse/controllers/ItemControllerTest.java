package com.example.authcourse.controllers;

import com.example.authcourse.model.persistence.Item;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class ItemControllerTest {

    @Autowired
    private ItemController itemController;

    @Test
    public void getItems() {
        ResponseEntity<List<Item>> responseEntity = itemController.getItems();
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void getItemById() {
        ResponseEntity<Item> itemResponseEntity = itemController.getItemById(1L);
        Assertions.assertEquals(200, itemResponseEntity.getStatusCodeValue());
    }

    @Test
    public void getItemsByNameSuccess() {
        ResponseEntity<List<Item>> itemResponseEntity = itemController.getItemsByName("Round Widget");
        Assertions.assertEquals(200, itemResponseEntity.getStatusCodeValue());
    }

    @Test
    public void getItemsByNameFailNotFound() {
        ResponseEntity<List<Item>> itemResponseEntity = itemController.getItemsByName("Not Found");
        Assertions.assertEquals(404, itemResponseEntity.getStatusCodeValue());
    }
}