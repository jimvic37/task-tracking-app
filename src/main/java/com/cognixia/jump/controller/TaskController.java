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
import com.cognixia.jump.model.Task;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.TaskRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class TaskController {
	@Autowired
	TaskRepository repo;

	@Autowired
	PasswordEncoder encoder;

	@GetMapping("/task")
	@Operation(summary = "Gets all tasks", description = "Returns a list of all tasks")
	@ApiResponse(responseCode = "200", description = "Ok")
	public List<Task> getTasks() {

		return repo.findAll();
	}

	@GetMapping("/task/{id}")
	@Operation(summary = "Gets task by ID", description = "Returns a task with the ID")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Ok"),
		@ApiResponse(responseCode = "404", description = "Task not found")
	})
	public ResponseEntity<?> getTaskById(@PathVariable int id) {

		Optional<Task> task = repo.findById(id);

		if(task.isEmpty()) {
			return ResponseEntity.status(404).body("Task not found");
		}
		else {
			return ResponseEntity.status(200).body(task.get());
		}
	}

    @PostMapping("/task")
	@Operation(summary = "Creates task", description = "Creates a task and returns the created trainer")
	@ApiResponse(responseCode = "201", description = "Ok")
	public ResponseEntity<?> createTask(@RequestBody Task task ) {

		task.setId(null);

		Task created = repo.save(task);

		return ResponseEntity.status(201).body(created);
	}

	@PutMapping("/task")
	@Operation(summary = "Updates task", description = "Updates a task and returns the updated tasl")
	@ApiResponse(responseCode = "200", description = "Ok")
	public ResponseEntity<?> updateTask(@RequestBody Task task) throws ResourceNotFoundException {

		if (repo.existsById(task.getId())) {
			Task updated = repo.save(task);
			return ResponseEntity.status(200).body(updated);
		}

		throw new ResourceNotFoundException("Task", task.getId());
	}

	@DeleteMapping("/task/{id}")
	@Operation(summary = "Deletes task by ID", description = "Deletes a task and returns the deleted task")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Ok"),
		@ApiResponse(responseCode = "404", description = "Task not found")
	})
	public ResponseEntity<Task> deleteTask(@PathVariable int id) throws ResourceNotFoundException {

		Optional<Task> found = repo.findById(id);

		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Task", id);
		}

		repo.deleteById(id);

		return ResponseEntity.status(200).body(found.get());
	}
}
