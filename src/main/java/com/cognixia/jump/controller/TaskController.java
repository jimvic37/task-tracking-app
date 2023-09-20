package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.cognixia.jump.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class TaskController {
	@Autowired
	TaskRepository taskRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	PasswordEncoder encoder;

	@GetMapping("/task")
	@Operation(summary = "Gets all tasks", description = "Returns a list of all tasks")
	@ApiResponse(responseCode = "200", description = "Ok")
	public List<Task> getTasks() {

		return taskRepo.findAll();
	}

	@GetMapping("/task/{id}")
	@Operation(summary = "Gets task by ID", description = "Returns a task with the ID")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Ok"),
		@ApiResponse(responseCode = "404", description = "Task not found")
	})
	public ResponseEntity<?> getTaskById(@PathVariable int id) {

		Optional<Task> task = taskRepo.findById(id);

		if(task.isEmpty()) {
			return ResponseEntity.status(404).body("Task not found");
		}
		else {
			return ResponseEntity.status(200).body(task.get());
		}
	}

	 @PostMapping("/task")
	 public ResponseEntity<Task> createTask(@RequestBody Task task, @AuthenticationPrincipal UserDetails userDetails) {
		 // Get the currently authenticated user's username
	     String username = userDetails.getUsername();

	     // Find the user by username
	     User user = userRepo.findByUsername(username);

	     if (user != null) {
	         // Set the user for the task
	         task.setUser(user);
	         // Save the task
	         Task createdTask = taskRepo.save(task);
	         return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
	     } else {
	         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	     }
	 }
	 
	 @GetMapping("/task/user/{username}")
	 @Operation(summary = "Gets tasks by username", description = "Returns a list of tasks for a specific user by username")
	 @ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Ok"),
	     @ApiResponse(responseCode = "404", description = "Task not found")
	 })
	 public ResponseEntity<?> findTaskByUserName(@PathVariable String username) {

	     User user = userRepo.findByUsername(username);

	     if (user != null) {
	         List<Task> tasks = taskRepo.findByUserUsername(username);
	         return ResponseEntity.status(200).body(tasks);
	     } else {
	         return ResponseEntity.status(404).body("User not found");
	     }
	 }

	@PutMapping("/task")
	@Operation(summary = "Updates task", description = "Updates a task and returns the updated tasl")
	@ApiResponse(responseCode = "200", description = "Ok")
	public ResponseEntity<?> updateTask(@RequestBody Task task) throws ResourceNotFoundException {

		if (taskRepo.existsById(task.getId())) {
			Task updated = taskRepo.save(task);
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

		Optional<Task> found = taskRepo.findById(id);

		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Task", id);
		}

		taskRepo.deleteById(id);

		return ResponseEntity.status(200).body(found.get());
	}
}
