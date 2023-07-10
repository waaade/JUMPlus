package com.cognixia.FurnitureAPI.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.FurnitureAPI.model.User;
import com.cognixia.FurnitureAPI.repository.UserRepository;
import com.cognixia.jump.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	UserRepository repo;
	
	@GetMapping("/user")
	public List<User> getUsers() {
		return repo.findAll();
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getUser(@PathVariable int id) throws ResourceNotFoundException {
		Optional<User> found = repo.findById(id);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("User with ID " + id + " not found");
		}
		return ResponseEntity.status(200).body(found.get());
	}
	
	@PostMapping("/user")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		user.setId(-1);
		User created = repo.save(user);
		
		return ResponseEntity.status(201).body(created);
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable int id) throws ResourceNotFoundException {
		Optional<User> found = repo.findById(id);
		if( found.isEmpty() ) {
			
			throw new ResourceNotFoundException("User with id = " + id + " was not found");
		}
		repo.deleteById(id);
		return ResponseEntity.status(200).body(found.get());
	}
}
