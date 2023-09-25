package com.example.authcourse.controllers;

import com.example.authcourse.model.persistence.Item;
import com.example.authcourse.model.persistence.repositories.ItemRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@RestController
@RequestMapping("/api/item")
public class ItemController {

    private final Logger logger = LogManager.getLogger(ItemController.class);

    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public ResponseEntity<List<Item>> getItems() {
        logger.info("getItems: Start...");
        logger.info("getItems: Success");
        logger.info("getItems: End");
        return ResponseEntity.ok(itemRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        logger.info("getItemById: Start...");
        logger.info("getItemById: request payload id = {}", id);
        logger.info("getItemById: Success");
        logger.info("getItemById: End");
        return ResponseEntity.of(itemRepository.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Item>> getItemsByName(@PathVariable String name) {
		logger.info("getItemsByName: Start...");
		logger.info("getItemsByName: request payload name = {}", name);

        List<Item> items = itemRepository.findByName(name);

		if (items == null || items.isEmpty()) {
			logger.error("getItemsByName: Item not found");
			logger.info("getItemsByName: End");
			return ResponseEntity.notFound().build();
		}

		logger.info("getItemsByName: Success");
		logger.info("getItemsByName: End");
        return ResponseEntity.ok(items);
    }

}
