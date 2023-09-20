package com.cognixia.jump.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>{

	List<Task> findByUserUsername(String username);
	
}
