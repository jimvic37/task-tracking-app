package com.cognixia.jump.controller;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.model.Task;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.TaskRepository;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.util.JwtUtil;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {
	private static final String STARTING_URI = "http://localhost:8080";
	
	@MockBean
	private PasswordEncoder encoder;
	
	@MockBean
	private TaskRepository taskRepo;
	
	@MockBean
	private UserRepository userRepo;
	
	@InjectMocks
	private TaskController controller;
	
	@MockBean
	private MyUserDetailsService service;
	
	@MockBean
	private JwtUtil jwtUtil;
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private JwtDecoder jwtDecoder;
	
	@Test
	public void testGetTasks() throws Exception {
		
		String uri = STARTING_URI + "/task";
		
		List<Task> tasks = new ArrayList<>();
		
		tasks.add(new Task(null, "task1", "description1", true, new User(1, "Ash", "pw123", User.Role.ROLE_USER, true, "a.ketchum@email.com", null)));
		tasks.add(new Task(null, "task2", "description2", true, new User(2, "Brock", "pw123", User.Role.ROLE_USER, true, "b.ketchum@email.com", null)));
		
		when(taskRepo.findAll()).thenReturn(tasks);
		
		mvc.perform(get(uri)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.length()").value(tasks.size()))
			.andExpect(jsonPath("$[0].id").value(tasks.get(0).getId()))
			.andExpect(jsonPath("$[0].name").value(tasks.get(0).getName()))
			.andExpect(jsonPath("$[0].description").value(tasks.get(0).getDescription()))
			.andExpect(jsonPath("$[0].isCompleted").value(tasks.get(0).getIsCompleted()))
			.andExpect(jsonPath("$[1].id").value(tasks.get(1).getId()))
			.andExpect(jsonPath("$[1].name").value(tasks.get(1).getName()))
			.andExpect(jsonPath("$[1].description").value(tasks.get(1).getDescription()))
			.andExpect(jsonPath("$[1].isCompleted").value(tasks.get(1).getIsCompleted()));
		
		verify(taskRepo, times(1)).findAll();
		verifyNoMoreInteractions(taskRepo);
	}

	@Test
	public void testGetTaskById() throws Exception {
		
		int id = 1;
		String uri = STARTING_URI + "/task/{id}";
		
		Optional<Task> task = Optional.of(new Task(null, "task1", "description1",true, null));
		
		when(taskRepo.findById(id)).thenReturn(task);
				
		mvc.perform(get(uri, id)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.id").value(task.get().getId()))
			.andExpect(jsonPath("$.name").value(task.get().getName()))
			.andExpect(jsonPath("$.description").value(task.get().getDescription()))
			.andExpect(jsonPath("$.isCompleted").value(task.get().getIsCompleted()));


		verify(taskRepo, times(1)).findById(id);
		verifyNoMoreInteractions(taskRepo);
	}

	@Test
	public void testGetTaskByIdTaskNotFound() throws Exception {
		
		int id = 1;
		String uri = STARTING_URI + "/task/{id}";
		Optional<Task> empty = Optional.empty();
		
		when(taskRepo.findById(id)).thenReturn(empty);
		
		mvc.perform(get(uri, id)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isNotFound());
		
  		verify(taskRepo, times(1)).findById(id);
  		verifyNoMoreInteractions(taskRepo);
  	}
	
	@Test
	public void testCreateTask() throws Exception {
	    String uri = STARTING_URI + "/task";

	    Task task = new Task(1, "task1", "description1", true, new User(1, "Ash", "pw123", User.Role.ROLE_USER, true, "a.ketchum@email.com", null));

	    when(taskRepo.save(Mockito.any(Task.class))).thenReturn(task);

	    ObjectMapper objectMapper = new ObjectMapper();
	    String taskJson = objectMapper.writeValueAsString(task);

	    mvc.perform(post(uri)
	            .content(taskJson)  // Send the JSON representation
	            .contentType(MediaType.APPLICATION_JSON_VALUE)
	            .with(SecurityMockMvcRequestPostProcessors.jwt()))
	            .andDo(print())
	            .andExpect(status().isCreated())
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
	            .andExpect(jsonPath("$.id").value(task.getId()))
	            .andExpect(jsonPath("$.name").value(task.getName()))
	            .andExpect(jsonPath("$.description").value(task.getDescription()))
	            .andExpect(jsonPath("$.isCompleted").value(task.getIsCompleted()));

	    verify(taskRepo, times(1)).save(Mockito.any(Task.class));
	}
	
	@Test
	public void testUpdateTask() throws Exception {
		
		String uri = STARTING_URI + "/task";
		
  		Task task = new Task(1, "task1", "description1", true, new User(1, "Ash", "pw123", User.Role.ROLE_USER, true, "a.ketchum@email.com", null));
		
		when(taskRepo.existsById(Mockito.any(Integer.class))).thenReturn(true);
		when(taskRepo.save(Mockito.any(Task.class))).thenReturn(task);
		
		mvc.perform(put(uri)
			.content(task.toJson())
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.id").value(task.getId()))
			.andExpect(jsonPath("$.name").value(task.getName()))
			.andExpect(jsonPath("$.description").value(task.getDescription()))
			.andExpect(jsonPath("$.isCompleted").value(task.getIsCompleted()));
		
		verify(taskRepo, times(1)).existsById(Mockito.any(Integer.class));
		verify(taskRepo, times(1)).save(Mockito.any(Task.class));
	}
	
	@Test
	public void testUpdateTaskTaskNotFound() throws Exception {
		
		String uri = STARTING_URI + "/task";
		
  		Task task = new Task(1, "task1", "description1", true, new User(1, "Ash", "pw123", User.Role.ROLE_USER, true, "a.ketchum@email.com", null));
		
		when(taskRepo.existsById(Mockito.any(Integer.class))).thenReturn(false);
		
		mvc.perform(put(uri)
			.content(task.toJson())
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
		
		verify(taskRepo, times(1)).existsById(Mockito.any(Integer.class));
		verifyNoMoreInteractions(taskRepo);
	}
	
	@Test
	public void testDeleteTask() throws Exception {
		
		int id = 1;
		String uri = STARTING_URI + "/task/{id}";
		Optional<Task> trainer = Optional.of(new Task(null, "task1", "description1", true, null));
		
		when(taskRepo.findById(id)).thenReturn(trainer);
		
		mvc.perform(delete(uri, id)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isOk());
		
		verify(taskRepo, times(1)).findById(Mockito.any(Integer.class));
		verify(taskRepo, times(1)).deleteById(Mockito.any(Integer.class));
	}
	
	@Test
	public void testDeleteTaskTaskNotFound() throws Exception {
		
		int id = 1;
		String uri = STARTING_URI + "/task/{id}";
		
		when(taskRepo.findById(id)).thenReturn(Optional.empty());
		
		mvc.perform(delete(uri, id)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isNotFound());
		
		verify(taskRepo, times(1)).findById(Mockito.any(Integer.class));
		verifyNoMoreInteractions(taskRepo);
	}
}
