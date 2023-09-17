package com.cognixia.jump.controller;

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

import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.util.JwtUtil;

@WebMvcTest(UserController.class)
public class UserControllerTest {
	
	private static final String STARTING_URI = "http://localhost:8080";
	
	@MockBean
	private PasswordEncoder encoder;
	
	@MockBean
	private UserRepository repo;
	
	@InjectMocks
	private UserController controller;
	
	@MockBean
	private MyUserDetailsService service;
	
	@MockBean
	private JwtUtil jwtUtil;
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private JwtDecoder jwtDecoder;
	
	@Test
	public void testGetUsers() throws Exception {
		
		String uri = STARTING_URI + "/user";
		
		List<User> users = new ArrayList<>();
		
		users.add(new User(null, "Ash", "pw123", User.Role.ROLE_USER, true, "a.ketchum@email.com", null));
		users.add(new User(null, "Brock", "pw123", User.Role.ROLE_USER, true, "b.harrison@email.com", null));
		
		when(repo.findAll()).thenReturn(users);
		
		mvc.perform(get(uri)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.length()").value(users.size()))
			.andExpect(jsonPath("$[0].id").value(users.get(0).getId()))
			.andExpect(jsonPath("$[0].username").value(users.get(0).getUsername()))
			.andExpect(jsonPath("$[0].password").value(users.get(0).getPassword()))
			.andExpect(jsonPath("$[0].enabled").value(users.get(0).isEnabled()))
			.andExpect(jsonPath("$[0].email").value(users.get(0).getEmail()))
			.andExpect(jsonPath("$[1].id").value(users.get(1).getId()))
			.andExpect(jsonPath("$[1].username").value(users.get(1).getUsername()))
			.andExpect(jsonPath("$[1].password").value(users.get(1).getPassword()))
			.andExpect(jsonPath("$[1].enabled").value(users.get(1).isEnabled()))
			.andExpect(jsonPath("$[1].email").value(users.get(1).getEmail()));
		
		verify(repo, times(1)).findAll();
		verifyNoMoreInteractions(repo);
	}

	@Test
	public void testGetUserById() throws Exception {
		
		int id = 1;
		String uri = STARTING_URI + "/user/{id}";
		
		Optional<User> user = Optional.of(new User(id, "Ash", "pw123", User.Role.ROLE_USER, true, "a.ketchum@email.com", null));
		
		when(repo.findById(id)).thenReturn(user);
		
		System.out.println(user.get().getUsername());
		
		mvc.perform(get(uri, id)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.id").value(user.get().getId()))
			.andExpect(jsonPath("$.username").value(user.get().getUsername()))
			.andExpect(jsonPath("$.password").value(user.get().getPassword()))
			.andExpect(jsonPath("$.enabled").value(user.get().isEnabled()))
			.andExpect(jsonPath("$.email").value(user.get().getEmail()));
		
		verify(repo, times(1)).findById(id);
		verifyNoMoreInteractions(repo);
	}

	@Test
	public void testGetUserByIdUserNotFound() throws Exception {
		
		int id = 1;
		String uri = STARTING_URI + "/user/{id}";
		
		when(repo.findById(id)).thenReturn(Optional.empty());
		
		mvc.perform(get(uri, id)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isNotFound());
		
  		verify(repo, times(1)).findById(id);
  		verifyNoMoreInteractions(repo);
  	}
	
  	@Test
  	public void testCreateUser() throws Exception {
  		
  		String uri = STARTING_URI + "/user";
  		
  		User user = new User(1, "Ash", "pw123", User.Role.ROLE_USER, true, "a.ketchum@email.com", null);
  		
  		when(encoder.encode(user.getPassword())).thenReturn(user.getPassword());
  		when(repo.save(Mockito.any(User.class))).thenReturn(user);
  		
  		mvc.perform(post(uri)
  			.content(user.toJson())
  			.contentType(MediaType.APPLICATION_JSON_VALUE)
  			.with(SecurityMockMvcRequestPostProcessors.jwt()))
  			.andDo(print())
			.andExpect(status().isCreated())
  			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.id").value(user.getId()))
  			.andExpect(jsonPath("$.username").value(user.getUsername()))
			.andExpect(jsonPath("$.password").value(user.getPassword()))
  			.andExpect(jsonPath("$.enabled").value(user.isEnabled()))
			.andExpect(jsonPath("$.email").value(user.getEmail()));
  		
  		verify(encoder, times(1)).encode(Mockito.any(String.class));
		verify(repo, times(1)).save(Mockito.any(User.class));
	}
	
	@Test
	public void testUpdateUser() throws Exception {
		
		String uri = STARTING_URI + "/user";
		
		User user = new User(1, "Ash", "pw123", User.Role.ROLE_USER, true, "a.ketchum@email.com", null);
		
		when(repo.existsById(Mockito.any(Integer.class))).thenReturn(true);
		when(repo.save(Mockito.any(User.class))).thenReturn(user);
		
		mvc.perform(put(uri)
			.content(user.toJson())
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.id").value(user.getId()))
			.andExpect(jsonPath("$.username").value(user.getUsername()))
			.andExpect(jsonPath("$.password").value(user.getPassword()))
			.andExpect(jsonPath("$.enabled").value(user.isEnabled()))
			.andExpect(jsonPath("$.email").value(user.getEmail()));
		
		verify(repo, times(1)).existsById(Mockito.any(Integer.class));
		verify(repo, times(1)).save(Mockito.any(User.class));
	}
	
	@Test
	public void testUpdateUserUserNotFound() throws Exception {
		
		String uri = STARTING_URI + "/user";
		
		User user = new User(1, "Ash", "pw123", User.Role.ROLE_USER, true, "a.ketchum@email.com", null);
		
		when(repo.existsById(Mockito.any(Integer.class))).thenReturn(false);
		
		mvc.perform(put(uri)
			.content(user.toJson())
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
		
		verify(repo, times(1)).existsById(Mockito.any(Integer.class));
		verifyNoMoreInteractions(repo);
	}
	
	@Test
	public void testDeleteUser() throws Exception {
		
		int id = 1;
		String uri = STARTING_URI + "/user/{id}";
		Optional<User> trainer = Optional.of(new User(id, "Ash", "pw123", User.Role.ROLE_USER, true, "a.ketchum@email.com", null));
		
		when(repo.findById(id)).thenReturn(trainer);
		
		mvc.perform(delete(uri, id)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isOk());
		
		verify(repo, times(1)).findById(Mockito.any(Integer.class));
		verify(repo, times(1)).deleteById(Mockito.any(Integer.class));
	}
	
	@Test
	public void testDeleteUserUserNotFound() throws Exception {
		
		int id = 1;
		String uri = STARTING_URI + "/user/{id}";
		
		when(repo.findById(id)).thenReturn(Optional.empty());
		
		mvc.perform(delete(uri, id)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isNotFound());
		
		verify(repo, times(1)).findById(Mockito.any(Integer.class));
		verifyNoMoreInteractions(repo);
	}

	

	
}