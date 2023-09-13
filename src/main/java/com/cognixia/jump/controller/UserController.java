package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class UserController {
	@Autowired
	UserRepository repo;
	
	@Autowired
	PasswordEncoder encoder;
	
	@GetMapping("/user")
	@Operation(summary = "Gets all users", description = "Returns a list of all users")
	@ApiResponse(responseCode = "200", description = "Ok")
	public List<User> getUsers() {
		
		return repo.findAll();
	}
	
	@GetMapping("/user/{id}")
	@Operation(summary = "Gets user by ID", description = "Returns a user with the ID")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Ok"),
		@ApiResponse(responseCode = "404", description = "User not found")
	})
	public ResponseEntity<?> getUserById(@PathVariable int id) {
		
		Optional<User> user = repo.findById(id);
		
		if(user.isEmpty()) {
			return ResponseEntity.status(404).body("User not found");
		}
		else {
			return ResponseEntity.status(200).body(user.get());
		}
	}

	@PostMapping("/user")
	@Operation(summary = "Creates user", description = "Creates a user and returns the created trainer")
	@ApiResponse(responseCode = "201", description = "Ok")
	public ResponseEntity<?> createTrainer(@RequestBody User user ) {
		
		user.setId(null);
		
		// will take the plain text password and encode it before it is saved to the db
		// security isn't going to encode our passwords on its own
		user.setPassword( encoder.encode( user.getPassword() ) );
				
		User created = repo.save(user);
		
		return ResponseEntity.status(201).body(created);
	}
	
	@PutMapping("/user")
	@Operation(summary = "Updates user", description = "Updates a user and returns the updated user")
	@ApiResponse(responseCode = "200", description = "Ok")
	public ResponseEntity<?> updateUser(@RequestBody User user) throws ResourceNotFoundException {
		
		if (repo.existsById(user.getId())) {
			User updated = repo.save(user);
			return ResponseEntity.status(200).body(updated);
		}
		
		throw new ResourceNotFoundException("User", user.getId());
	}
	
	@DeleteMapping("/user/{id}")
	@Operation(summary = "Deletes user by ID", description = "Deletes a user and returns the deleted user")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Ok"),
		@ApiResponse(responseCode = "404", description = "User not found")
	})
	public ResponseEntity<User> deleteUser(@PathVariable int id) throws ResourceNotFoundException {
		
		Optional<User> found = repo.findById(id);
		
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("User", id);
		}
		
		repo.deleteById(id);
		
		return ResponseEntity.status(200).body(found.get());
	}
}
