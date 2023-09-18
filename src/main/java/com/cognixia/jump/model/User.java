package com.cognixia.jump.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static enum Role {
		ROLE_USER, ROLE_ADMIN		// roles should start with ROLE_ and be all capital letters
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	// unique and not null username
	@Column(unique = true, nullable = false)
	@NotBlank
	@Schema(description = "Username of the user", example = "ash123", required = true)
	private String username;
	
	@Column(nullable = false)
	@NotBlank
	@Schema(description = "Password of the task", example = "pass123", required = true)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@Schema(description = "Role of the user", example = "ROLE_USER", required = true)
	private Role role;
	
	@Column( columnDefinition = "boolean default true" )
	private boolean enabled; // true/false if user enabled/disabled
	
	@Column(nullable = false)
	@Schema(description = "Email of the user", example = "ash123@gmail.com", required = true)
	private String email;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<UserTask> userTask;

	
	public User() {
		
	}

	public User(Integer id, @NotBlank String username, @NotBlank String password, Role role, boolean enabled,
			String email, List<UserTask> userTask) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
		this.email = email;
		this.userTask = userTask;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<UserTask> getUserTask() {
		return userTask;
	}

	public void setUserTask(List<UserTask> userTask) {
		this.userTask = userTask;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + ", enabled="
				+ enabled + ", email=" + email + ", userTask=" + userTask + "]";
	}

	public String toJson() {
	    try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        return objectMapper.writeValueAsString(this);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "{}"; // Return an empty JSON object in case of an error.
	    }
	}
}




