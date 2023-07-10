package com.cognixia.FurnitureAPI.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.FurnitureAPI.exception.OutOfStockException;
import com.cognixia.FurnitureAPI.model.Item;
import com.cognixia.FurnitureAPI.model.Order;
import com.cognixia.FurnitureAPI.model.User;
import com.cognixia.FurnitureAPI.repository.ItemRepository;
import com.cognixia.FurnitureAPI.repository.OrderRepository;
import com.cognixia.FurnitureAPI.repository.UserRepository;
import com.cognixia.jump.exception.ResourceNotFoundException;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api")
public class OrderController {
	@Autowired
	OrderRepository repo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ItemRepository itemRepo;
	
	@GetMapping("/order")
	public List<Order> getOrders() {
		return repo.findAll();
	}
	
	@PostMapping("/order")
	public ResponseEntity<?> saveOrderToUser(@PathParam(value="userId") int userId, @PathParam(value="itemId") int itemId) throws ResourceNotFoundException, OutOfStockException {
		Optional <Item> itemFound = itemRepo.findById(itemId);
		Optional <User> userFound = userRepo.findById(userId);
		
		if(userFound.isEmpty()) {
            throw new ResourceNotFoundException("User");
        }
		else if (itemFound.isEmpty()) {
			throw new ResourceNotFoundException("Item");
		}
		
		if (itemFound.get().getStock() < 1) {
			throw new OutOfStockException();
		}
		
		Order newOrder = new Order(0, userFound.get(), itemFound.get());
		Order createdOrder = repo.save(newOrder);
		
		return ResponseEntity.status(201).body(createdOrder);
		
	}
}
