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
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.UserTask;
import com.cognixia.jump.repository.UserTaskRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class UserTaskController {
	@Autowired
	UserTaskRepository repo;

	@Autowired
	PasswordEncoder encoder;

	@GetMapping("/userTask")
	@Operation(summary = "Gets all user tasks", description = "Returns a list of all user tasks")
	@ApiResponse(responseCode = "200", description = "Ok")
	public List<UserTask> getTasks() {

		return repo.findAll();
	}

	@GetMapping("/userTask/{id}")
	@Operation(summary = "Gets user_task by ID", description = "Returns a user_task with the ID")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Ok"),
		@ApiResponse(responseCode = "404", description = "User task not found")
	})
	public ResponseEntity<?> getTaskById(@PathVariable int id) {

		Optional<UserTask> task = repo.findById(id);

		if(task.isEmpty()) {
			return ResponseEntity.status(404).body("User Task not found");
		}
		else {
			return ResponseEntity.status(200).body(task.get());
		}
	}

    @PostMapping("/userTask")
	@Operation(summary = "Creates task", description = "Creates a task and returns the created trainer")
	@ApiResponse(responseCode = "201", description = "Ok")
	public ResponseEntity<?> createTrainer(@RequestBody UserTask task ) {

		task.setId(null);

		UserTask created = repo.save(task);

		return ResponseEntity.status(201).body(created);
	}

	@PutMapping("/userTask")
	@Operation(summary = "Updates user_task", description = "Updates a user_task and returns the updated user_task")
	@ApiResponse(responseCode = "200", description = "Ok")
	public ResponseEntity<?> updateTask(@RequestBody UserTask task) throws ResourceNotFoundException {

		if (repo.existsById(task.getId())) {
			UserTask updated = repo.save(task);
			return ResponseEntity.status(200).body(updated);
		}

		throw new ResourceNotFoundException("Task", task.getId());
	}

	@DeleteMapping("/userTask/{id}")
	@Operation(summary = "Deletes task by ID", description = "Deletes a task and returns the deleted task")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Ok"),
		@ApiResponse(responseCode = "404", description = "Task not found")
	})
	public ResponseEntity<UserTask> deleteTask(@PathVariable int id) throws ResourceNotFoundException {

		Optional<UserTask> found = repo.findById(id);

		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Task", id);
		}

		repo.deleteById(id);

		return ResponseEntity.status(200).body(found.get());
	}
}
