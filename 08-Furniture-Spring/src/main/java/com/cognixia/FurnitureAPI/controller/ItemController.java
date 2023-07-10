package com.cognixia.FurnitureAPI.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.FurnitureAPI.model.Item;
import com.cognixia.FurnitureAPI.repository.ItemRepository;
import com.cognixia.jump.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api")
public class ItemController {
	@Autowired
	ItemRepository repo;
	
	@GetMapping("/item")
	public List<Item> getItems() {
		return repo.findAll();
	}
	
	@GetMapping("/item/{id}")
	public ResponseEntity<?> getItem(@PathVariable int id) throws ResourceNotFoundException {
		Optional<Item> found = repo.findById(id);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Item with ID " + id + " not found");
		}
		return ResponseEntity.status(200).body(found.get());
	}
	
	@PostMapping("/item")
	public ResponseEntity<Item> createItem(@RequestBody Item item) {
		item.setId(-1);
		Item created = repo.save(item);
		
		return ResponseEntity.status(201).body(created);
	}
	
	@PutMapping("/item/{id}")
	public ResponseEntity<?> updateItem(@PathVariable int id, @RequestBody Item item) throws ResourceNotFoundException {
		Optional<Item> found = repo.findById(id);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Item with ID " + id + " not found");
		}
		Item updated = repo.save(item);
		
		return ResponseEntity.status(201).body(updated);
	}
	
	@DeleteMapping("/item/{id}")
	public ResponseEntity<?> deleteItem(@PathVariable int id) throws ResourceNotFoundException {
		Optional<Item> found = repo.findById(id);
		if (found.isEmpty() ) {
			
			throw new ResourceNotFoundException("Item with id = " + id + " was not found");
		}
		repo.deleteById(id);
		return ResponseEntity.status(200).body(found.get());
	}
}
