//package com.cognixia.jump.controller;
//
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.cognixia.jump.model.Task;
//import com.cognixia.jump.model.User;
//import com.cognixia.jump.model.UserTask;
//import com.cognixia.jump.repository.UserTaskRepository;
//import com.cognixia.jump.service.MyUserDetailsService;
//import com.cognixia.jump.util.JwtUtil;
//
//@WebMvcTest(UserTaskController.class)
//public class UserTaskControllerTest {
//	private static final String STARTING_URI = "http://localhost:8080";
//
//	@MockBean
//	private PasswordEncoder encoder;
//	
//	@MockBean
//	private UserTaskRepository repo;
//	
//	@InjectMocks
//	private UserTaskController controller;
//	
//	@MockBean
//	private MyUserDetailsService service;
//	
//	@MockBean
//	private JwtUtil jwtUtil;
//	
//	@Autowired
//	private MockMvc mvc;
//	
//	@MockBean
//	private JwtDecoder jwtDecoder;
//	
//	@Test
//	public void testGetUserTasks() throws Exception {
//		String uri = STARTING_URI + "/userTask";
//		
//		List<UserTask> userTasks = new ArrayList<>();
//		
//		userTasks.add(new UserTask(null, new Task(null, "task1", "description1", null), new User(null, "Ash", "pw123", User.Role.ROLE_USER, true, "a.ketchum@email.com", null), null, true));
//		userTasks.add(new UserTask(null, new Task(null, "task2", "description2", null), new User(null, "Brock", "pw123", User.Role.ROLE_USER, true, "b.ketchum@email.com", null), null, false));
//		
//		when(repo.findAll()).thenReturn(userTasks);
//		
//		mvc.perform(get(uri)
//				.with(SecurityMockMvcRequestPostProcessors.jwt()))
//				.andDo(print())
//				.andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(jsonPath("$.length()").value(userTasks.size()))
//				.andExpect(jsonPath("$[0].id").value(userTasks.get(0).getId()))
//				.andExpect( jsonPath("$[0].task.id").value(userTasks.get(0).getTask().getId() ) )
//				.andExpect( jsonPath("$[0].task.name").value(userTasks.get(0).getTask().getName() ) )
//				.andExpect( jsonPath("$[0].task.description").value(userTasks.get(0).getTask().getDescription() ) )
//				.andExpect( jsonPath("$[0].task.userTask").doesNotExist())
//				.andExpect( jsonPath("$[0].user.id").value(userTasks.get(0).getUser().getId() ) )
//				.andExpect( jsonPath("$[0].user.username").value(userTasks.get(0).getUser().getUsername() ) )
//				.andExpect( jsonPath("$[0].user.password").value(userTasks.get(0).getUser().getPassword() ) )
//				.andExpect( jsonPath("$[0].user.role").value(userTasks.get(0).getUser().getRole().toString() ) )
//				.andExpect( jsonPath("$[0].user.enabled").value(userTasks.get(0).getUser().isEnabled() ) )
//				.andExpect( jsonPath("$[0].user.email").value(userTasks.get(0).getUser().getEmail() ) )
//				.andExpect( jsonPath("$[0].user.team").doesNotExist())
//				.andExpect(jsonPath("$[0].currTime").value(userTasks.get(0).getCurrTime()))
//				.andExpect(jsonPath("$[0].isCompleted").value(userTasks.get(0).getIsCompleted()))
//				.andExpect(jsonPath("$[1].id").value(userTasks.get(1).getId()))
//				.andExpect( jsonPath("$[1].task.id").value(userTasks.get(1).getTask().getId() ) )
//				.andExpect( jsonPath("$[1].task.name").value(userTasks.get(1).getTask().getName() ) )
//				.andExpect( jsonPath("$[1].task.description").value(userTasks.get(1).getTask().getDescription() ) )
//				.andExpect( jsonPath("$[1].task.userTask").doesNotExist())
//				.andExpect( jsonPath("$[1].user.id").value(userTasks.get(1).getUser().getId() ) )
//				.andExpect( jsonPath("$[1].user.username").value(userTasks.get(1).getUser().getUsername() ) )
//				.andExpect( jsonPath("$[1].user.password").value(userTasks.get(1).getUser().getPassword() ) )
//				.andExpect( jsonPath("$[1].user.role").value(userTasks.get(1).getUser().getRole().toString() ) )
//				.andExpect( jsonPath("$[1].user.enabled").value(userTasks.get(1).getUser().isEnabled() ) )
//				.andExpect( jsonPath("$[1].user.email").value(userTasks.get(1).getUser().getEmail() ) )
//				.andExpect( jsonPath("$[1].user.team").doesNotExist())
//				.andExpect(jsonPath("$[1].currTime").value(userTasks.get(1).getCurrTime()))
//				.andExpect(jsonPath("$[1].isCompleted").value(userTasks.get(1).getIsCompleted()));
//
//			
//		verify(repo, times(1)).findAll();
//		verifyNoMoreInteractions(repo);
//	}
//	
//	
//	@Test
//	public void testGetUserTaskById() throws Exception {
//		int id = 1;
//		String uri = STARTING_URI + "/userTask/{id}";
//		Optional<UserTask> userTask = Optional.ofNullable(new UserTask(id, new Task(null, "task1", "description1", null), new User(null, "Ash", "pw123", User.Role.ROLE_USER, true, "a.ketchum@email.com", null), null, true));
//		
//		when( repo.findById(userTask.get().getId()) ).thenReturn(userTask);
//		
//		mvc.perform(get(uri,id)
//			.with(SecurityMockMvcRequestPostProcessors.jwt()))
//			.andDo( print()) 
//			.andExpect( status().isOk() )
//			.andExpect( content().contentType( MediaType.APPLICATION_JSON_VALUE ) ) 
//			.andExpect(jsonPath("$.id").value(userTask.get().getId()))
//			.andExpect( jsonPath("$.task.id").value(userTask.get().getTask().getId() ) )
//			.andExpect( jsonPath("$.task.name").value(userTask.get().getTask().getName() ) )
//			.andExpect( jsonPath("$.task.description").value(userTask.get().getTask().getDescription() ) )
//			.andExpect( jsonPath("$.task.userTask").doesNotExist())
//			.andExpect( jsonPath("$.user.id").value(userTask.get().getUser().getId() ) )
//			.andExpect( jsonPath("$.user.username").value(userTask.get().getUser().getUsername() ) )
//			.andExpect( jsonPath("$.user.password").value(userTask.get().getUser().getPassword() ) )
//			.andExpect( jsonPath("$.user.role").value(userTask.get().getUser().getRole().toString() ) )
//			.andExpect( jsonPath("$.user.enabled").value(userTask.get().getUser().isEnabled() ) )
//			.andExpect( jsonPath("$.user.email").value(userTask.get().getUser().getEmail() ) )
//			.andExpect( jsonPath("$.user.team").doesNotExist())
//			.andExpect(jsonPath("$.currTime").value(userTask.get().getCurrTime()))
//			.andExpect(jsonPath("$.isCompleted").value(userTask.get().getIsCompleted()));
//		
//		verify( repo, times(1) ).findById(userTask.get().getId());
//		verifyNoMoreInteractions(repo);
//	}
//	
//	@Test
//	public void testGetUserTaskByIdUserTaskNotFound() throws Exception {
//		
//		int id = 1;
//		String uri = STARTING_URI + "/userTask/{id}";
//		
//		when(repo.findById(id)).thenReturn(Optional.empty());
//		
//		mvc.perform(get(uri, id)
//			.with(SecurityMockMvcRequestPostProcessors.jwt()))
//			.andDo(print())
//			.andExpect(status().isNotFound());
//		
//  		verify(repo, times(1)).findById(id);
//  		verifyNoMoreInteractions(repo);
//  	}
//	
//  	@Test
//  	public void testCreateUserTask() throws Exception {
//  		
//  		String uri = STARTING_URI + "/userTask";
//  		
//  		UserTask userTask = new UserTask(null, new Task(null, "task1", "description1", null), new User(null, "Ash", "pw123", User.Role.ROLE_USER, true, "a.ketchum@email.com", null), null, true);
//  		
//  		when(repo.save(Mockito.any(UserTask.class))).thenReturn(userTask);
//  		
//  		mvc.perform(post(uri)
//  			.content(userTask.toJson())
//  			.contentType(MediaType.APPLICATION_JSON_VALUE)
//  			.with(SecurityMockMvcRequestPostProcessors.jwt()))
//  			.andDo(print())
//			.andExpect(status().isCreated())
//  			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//  			.andExpect(jsonPath("$.id").value(userTask.getId()))
//			.andExpect( jsonPath("$.task.id").value(userTask.getTask().getId() ) )
//			.andExpect( jsonPath("$.task.name").value(userTask.getTask().getName() ) )
//			.andExpect( jsonPath("$.task.description").value(userTask.getTask().getDescription() ) )
//			.andExpect( jsonPath("$.task.userTask").doesNotExist())
//			.andExpect( jsonPath("$.user.id").value(userTask.getUser().getId() ) )
//			.andExpect( jsonPath("$.user.username").value(userTask.getUser().getUsername() ) )
//			.andExpect( jsonPath("$.user.password").value(userTask.getUser().getPassword() ) )
//			.andExpect( jsonPath("$.user.role").value(userTask.getUser().getRole().toString() ) )
//			.andExpect( jsonPath("$.user.enabled").value(userTask.getUser().isEnabled() ) )
//			.andExpect( jsonPath("$.user.email").value(userTask.getUser().getEmail() ) )
//			.andExpect( jsonPath("$.user.team").doesNotExist())
//			.andExpect(jsonPath("$.currTime").value(userTask.getCurrTime()))
//			.andExpect(jsonPath("$.isCompleted").value(userTask.getIsCompleted()));
//  		
//		verify(repo, times(1)).save(Mockito.any(UserTask.class));
//	}
//	
//	@Test
//	public void testUpdateUserTask() throws Exception {
//		
//		String uri = STARTING_URI + "/userTask";
//		
//  		UserTask userTask = new UserTask(1, new Task(null, "task1", "description1", null), new User(null, "Ash", "pw123", User.Role.ROLE_USER, true, "a.ketchum@email.com", null), null, true);
//		
//		when(repo.existsById(Mockito.any(Integer.class))).thenReturn(true);
//		when(repo.save(Mockito.any(UserTask.class))).thenReturn(userTask);
//		
//		mvc.perform(put(uri)
//			.content(userTask.toJson())
//			.contentType(MediaType.APPLICATION_JSON_VALUE)
//			.with(SecurityMockMvcRequestPostProcessors.jwt()))
//			.andDo(print())
//			.andExpect(status().isOk())
//			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//  			.andExpect(jsonPath("$.id").value(userTask.getId()))
//			.andExpect( jsonPath("$.task.id").value(userTask.getTask().getId() ) )
//			.andExpect( jsonPath("$.task.name").value(userTask.getTask().getName() ) )
//			.andExpect( jsonPath("$.task.description").value(userTask.getTask().getDescription() ) )
//			.andExpect( jsonPath("$.task.userTask").doesNotExist())
//			.andExpect( jsonPath("$.user.id").value(userTask.getUser().getId() ) )
//			.andExpect( jsonPath("$.user.username").value(userTask.getUser().getUsername() ) )
//			.andExpect( jsonPath("$.user.password").value(userTask.getUser().getPassword() ) )
//			.andExpect( jsonPath("$.user.role").value(userTask.getUser().getRole().toString() ) )
//			.andExpect( jsonPath("$.user.enabled").value(userTask.getUser().isEnabled() ) )
//			.andExpect( jsonPath("$.user.email").value(userTask.getUser().getEmail() ) )
//			.andExpect( jsonPath("$.user.team").doesNotExist())
//			.andExpect(jsonPath("$.currTime").value(userTask.getCurrTime()))
//			.andExpect(jsonPath("$.isCompleted").value(userTask.getIsCompleted()));
//		
//		verify(repo, times(1)).existsById(Mockito.any(Integer.class));
//		verify(repo, times(1)).save(Mockito.any(UserTask.class));
//	}
//	
//	@Test
//	public void testUpdateUserTaskUserTaskNotFound() throws Exception {
//		
//		String uri = STARTING_URI + "/userTask";
//		
//  		UserTask userTask = new UserTask(1, new Task(1, "task1", "description1", null), new User(1, "Ash", "pw123", User.Role.ROLE_USER, true, "a.ketchum@email.com", null), null, true);
//		
//		when(repo.existsById(Mockito.any(Integer.class))).thenReturn(false);
//		
//		mvc.perform(put(uri)
//			.content(userTask.toJson())
//			.contentType(MediaType.APPLICATION_JSON_VALUE)
//			.with(SecurityMockMvcRequestPostProcessors.jwt()))
//			.andDo(print())
//			.andExpect(status().isNotFound())
//			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
//		
//		verify(repo, times(1)).existsById(Mockito.any(Integer.class));
//		verifyNoMoreInteractions(repo);
//	}
//	
//	@Test
//	public void testDeleteUserTask() throws Exception {
//		
//		int id = 1;
//		String uri = STARTING_URI + "/userTask/{id}";
//		Optional<UserTask> userTask = Optional.of(new UserTask(null, new Task(null, "task1", "description1", null), new User(null, "Ash", "pw123", User.Role.ROLE_USER, true, "a.ketchum@email.com", null), null, true));
//		
//		when(repo.findById(id)).thenReturn(userTask);
//		
//		mvc.perform(delete(uri, id)
//			.with(SecurityMockMvcRequestPostProcessors.jwt()))
//			.andDo(print())
//			.andExpect(status().isOk());
//		
//		verify(repo, times(1)).findById(Mockito.any(Integer.class));
//		verify(repo, times(1)).deleteById(Mockito.any(Integer.class));
//	}
//	
//	@Test
//	public void testDeleteUserTaskUserTaskNotFound() throws Exception {
//		
//		int id = 1;		
//		String uri = STARTING_URI + "/userTask/{id}";
//		
//		when(repo.findById(id)).thenReturn(Optional.empty());
//		
//		mvc.perform(delete(uri, id)
//			.with(SecurityMockMvcRequestPostProcessors.jwt()))
//			.andDo(print())
//			.andExpect(status().isNotFound());
//		
//		verify(repo, times(1)).findById(Mockito.any(Integer.class));
//		verifyNoMoreInteractions(repo);
//	}
//}
//
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	