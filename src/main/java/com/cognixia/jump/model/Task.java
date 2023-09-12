package com.cognixia.jump.model;

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

@Entity
public class Task implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = true)
	private String name;
	
	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private LocalDate time;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
	private List<UserTask> session;
	
	public Task() {
		
	}
	
	public Task(Integer id, String name, String description, LocalDate time, List<UserTask> session) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.time = time;
		this.session = session;
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

	public LocalDate getTime() {
		return time;
	}

	public void setTime(LocalDate time) {
		this.time = time;
	}

	public List<UserTask> getSession() {
		return session;
	}

	public void setSession(List<UserTask> session) {
		this.session = session;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", description=" + description + ", time=" + time + ", session="
				+ session + "]";
	}
	
	
	
}
