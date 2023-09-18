package com.cognixia.jump.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
public class Task implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = true)
	@Schema(description = "Name of the task", example = "Task 1", required = false)
	private String name;
	
	@Column(nullable = true)
	@Schema(description = "Description of the task", example = "Recrod video interview", required = false)
	private String description;

//	@Column(nullable = false)
//	@Schema(description = "Time of the task", example = "30:00", required = true)
//	private LocalDate time;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
	private List<UserTask> userTask;
	
	public Task() {
		
	}

	public Task(Integer id, String name, String description, List<UserTask> userTask) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.userTask = userTask;
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

	public List<UserTask> getUserTask() {
		return userTask;
	}

	public void setUserTask(List<UserTask> userTask) {
		this.userTask = userTask;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", description=" + description + ", userTask=" + userTask + "]";
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
