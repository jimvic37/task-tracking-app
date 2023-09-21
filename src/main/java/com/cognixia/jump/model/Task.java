package com.cognixia.jump.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import io.swagger.v3.oas.annotations.media.Schema;

@Entity
public class Task implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	@Schema(description = "Name of the task", example = "Task 1", required = false)
	private String name;
	
	@Column(nullable = false)
	@Schema(description = "Description of the task", example = "Recrod video interview", required = false)
	private String description;
	
	@Column(columnDefinition = "boolean default false")
	@Schema(description = "Status of the task", example = "Completed", required = false)
	private Boolean isCompleted;
	
	@ManyToOne
	@JoinColumn( name = "username", referencedColumnName = "username")
	private User user;
	
	public Task() {
		
	}
	
	
	public Task(Integer id, String name, String description, Boolean isCompleted, User user) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.isCompleted = isCompleted;
		this.user = user;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
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
