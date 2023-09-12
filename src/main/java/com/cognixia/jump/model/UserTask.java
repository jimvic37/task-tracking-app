package com.cognixia.jump.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class UserTask implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "task_id", referencedColumnName = "id")
	private Task task;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	@Column(nullable=false)
	private LocalDate currTime;
	
	@NotNull
	private Boolean isCompleted;
	
	public UserTask() {
	
	}
	
	public UserTask(Integer id, Task task, User user, LocalDate currTime, @NotNull Boolean isCompleted) {
		super();
		this.id = id;
		this.task = task;
		this.user = user;
		this.currTime = currTime;
		this.isCompleted = isCompleted;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDate getCurrTime() {
		return currTime;
	}

	public void setCurrent_time(LocalDate currTime) {
		this.currTime = currTime;
	}

	public Boolean getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	@Override
	public String toString() {
		return "Session [id=" + id + ", task=" + task + ", user=" + user + ", currTime=" + currTime
				+ ", isCompleted=" + isCompleted + "]";
	}
	
	
}
