package com.cognixia.jump.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.model.User;
import com.cognixia.jump.service.MyUserDetails;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.util.JwtUtil;

@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

	private static final String STARTING_URI = "http://localhost:8080";
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private AuthenticationManager authenticationManager;
	
	@MockBean
	private JwtUtil jwtUtil;
	
	@MockBean
	private AuthenticationController controller;
	
	@MockBean
	private MyUserDetailsService trainerDetailsService;
	
	@Test
	public void testCreateJwtToken() throws Exception {
		
		String uri = STARTING_URI + "/authenticate";
		
		User user = new User(null, "ash", "pw123", User.Role.ROLE_USER, true, "a.ketchum@email.com");
		
		String json = "{\"username\" : \"" + user.getUsername()
			+ "\", \"password\" : \"" + user.getPassword() + "\"}";
		
		MyUserDetails trainerDetails = new MyUserDetails(user);
		
		when(authenticationManager.authenticate(Mockito.any(Authentication.class))).thenReturn(null);
		when(trainerDetailsService.loadUserByUsername(user.getUsername())).thenReturn(trainerDetails);
		when(jwtUtil.generateTokens(trainerDetails)).thenReturn("jwt");
		
		mvc.perform(post(uri)
			.content(json)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	@Test
	public void testCreateJwtTokenBadCredentials() throws Exception {
		
		String uri = STARTING_URI + "/authenticate";
		
		User user = new User(null, "ash", "pw123", User.Role.ROLE_USER, true, "a.ketchum@email.com");
		
		String json = "{\"username\" : \"" + user.getUsername()
			+ "\", \"password\" : \"" + user.getPassword() + "\"}";
		
		MyUserDetails userDetals = new MyUserDetails(user);
		
		when(authenticationManager.authenticate(Mockito.any(Authentication.class))).thenThrow(new BadCredentialsException("msg"));
		when(trainerDetailsService.loadUserByUsername(user.getUsername())).thenReturn(userDetals);
		when(jwtUtil.generateTokens(userDetals)).thenReturn("jwt");
		
		mvc.perform(post(uri)
			.content(json)
			.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andDo(print())
			.andExpect(status().isForbidden());
	}
}
